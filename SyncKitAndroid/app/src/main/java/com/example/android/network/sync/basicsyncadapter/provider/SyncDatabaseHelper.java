package com.example.android.network.sync.basicsyncadapter.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.network.sync.basicsyncadapter.models.Transformer;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Veni on 11/25/14.
 */
public class SyncDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    /** Filename for SQLite file. */
    public static final String DATABASE_NAME = "SyncKitDemo.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";
    /** SQL statement to create "Transformer" table. */

    public ArrayList<Class> modelsRegisteredForSync;

    public SQLiteDatabase databaseObject;

    private static SyncDatabaseHelper singleton;
    private Context context;

    public static SyncDatabaseHelper getDataHelper(Context context) {
        if (singleton == null) {
            singleton = new SyncDatabaseHelper(context);
        }
        if(!singleton.databaseObject.isOpen()){
            SyncDatabaseHelper openHelper = new SyncDatabaseHelper(singleton.context);
            singleton.databaseObject = openHelper.getWritableDatabase();
        }
        singleton.context = context;
        return singleton;
    }


    public SyncDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context = context;
        modelsRegisteredForSync = new ArrayList<Class>();
        modelsRegisteredForSync.add(Transformer.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            for (Class sClass : modelsRegisteredForSync) {
                Method methodToGetCreateQueryOfClass = sClass.getDeclaredMethod("createTableQuery", null);
                String createTableQuery = (String) methodToGetCreateQueryOfClass.invoke(null,null);
                //create all tables
                db.execSQL(createTableQuery);
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            for (Class sClass : modelsRegisteredForSync) {
                Method methodToGetCreateQueryOfClass = sClass.getDeclaredMethod("deleteTableQuery", null);
                String deleteTableQuery = (String) methodToGetCreateQueryOfClass.invoke(null, null);
                //delete all old tables
                db.execSQL(deleteTableQuery);
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
            onCreate(db);

    }

    public boolean insertObject(Object obj)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Method getContentValuesMethod = obj.getClass().getDeclaredMethod("contentValuesForInsert", null);
            ContentValues insertValues = (ContentValues) getContentValuesMethod.invoke(obj,null);

            Method getTableNameMethod = obj.getClass().getDeclaredMethod("SQLITETableNameMethod",null);
            String tableName = (String) getTableNameMethod.invoke(null,null);

            db.insert(tableName,null,insertValues);

            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        return false;
    }

    public boolean udpateObject(Object obj)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Method getContentValuesMethod = obj.getClass().getDeclaredMethod("contentValuesForUpdate", null);
            ContentValues updateValues = (ContentValues) getContentValuesMethod.invoke(obj,null);

            Method getTableNameMethod = obj.getClass().getDeclaredMethod("SQLITETableNameMethod",null);
            String tableName = (String) getTableNameMethod.invoke(null,null);

            Method getColumnNameForIdentificationAttribute = obj.getClass().getDeclaredMethod("columnNameForIdentificationAttribute",null);
            String columnName = (String) getColumnNameForIdentificationAttribute.invoke(null,null);

            Method getIdentificationAttribtueValue = obj.getClass().getDeclaredMethod("identificationAttributeValue",null);
            String idValue = (String) getColumnNameForIdentificationAttribute.invoke(obj,null);

            db.update(tableName,updateValues,columnName + " = ?",new String[]{idValue});

            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        return false;
    }

    public Cursor selectAllObjectsOfClass(Class sClass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        try {
            Method getTableNameMethod = sClass.getDeclaredMethod("SQLITETableNameMethod",null);
            String tableName = (String) getTableNameMethod.invoke(null,null);
            c = db.rawQuery("SELECT  * FROM " + tableName,null);
            return c;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        return c;
    }

}
