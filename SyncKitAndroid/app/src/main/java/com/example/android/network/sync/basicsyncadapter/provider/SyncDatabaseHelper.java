package com.example.android.network.sync.basicsyncadapter.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.network.sync.basicsyncadapter.models.Transformer;
import com.example.android.network.sync.basicsyncadapter.util.Constants;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Veni on 11/25/14.
 */
public class SyncDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    /** Filename for SQLite file. */
    public static final String DATABASE_NAME = "SyncKitDemo.db";

    public ArrayList<Class> modelsRegisteredForSync;

    public SQLiteDatabase databaseObject;

    private static SyncDatabaseHelper singleton;
    private Context mContext;

    public static synchronized SyncDatabaseHelper getDataHelper(Context context,ArrayList<Class> modelsForSync) {
        if (singleton == null) {
            singleton = new SyncDatabaseHelper(context,modelsForSync);
        }

        singleton.mContext = context;
        return singleton;
    }

    public static synchronized SyncDatabaseHelper getDataHelper(Context context) {
        if (singleton == null) {
            singleton = new SyncDatabaseHelper(context);
        }

        singleton.mContext = context;
        return singleton;
    }


    public SyncDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;

    }

    public SyncDatabaseHelper(Context context,ArrayList<Class> modelsForSync)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        this.modelsRegisteredForSync = modelsForSync;

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

            long result = db.insert(tableName,null,insertValues);

            Cursor c = selectAllObjectsOfClass(obj.getClass());

                    while(c.moveToNext())
                    {
                        System.out.println(c.getInt(1));
                    }

            return result==-1?false:true;
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

            Method getIdentificationAttribtueValue = obj.getClass().getDeclaredMethod("identificationAttributeValue",null);
            Integer idValue = (Integer) getIdentificationAttribtueValue.invoke(obj,null);

            String whereClause = "client_id" + " = ?";

            String arr[] =  {idValue.toString()};

            int update = db.update(tableName,updateValues,whereClause,arr);

            return update>0?true:false;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        return false;
    }

    public boolean deletObject(Object obj)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Method getTableNameMethod = obj.getClass().getDeclaredMethod("SQLITETableNameMethod",null);
            String tableName = (String) getTableNameMethod.invoke(null,null);

            Method getIdentificationAttribtueValue = obj.getClass().getDeclaredMethod("identificationAttributeValue",null);
            Integer idValue = (Integer) getIdentificationAttribtueValue.invoke(obj,null);

            int update = db.delete(tableName,"client_id" + " = ?",new String[]{idValue.toString()});

            return update>0?true:false;
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

    public Cursor selectObjectsOfClassWithSyncFlag(Class sClass,Integer syncFlag)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        try {

            Method columnNameForSyncFlag = sClass.getDeclaredMethod("columnNameForSyncFlag",null);
            String columnNameSync = (String) columnNameForSyncFlag.invoke(null,null);

            Method getTableNameMethod = sClass.getDeclaredMethod("SQLITETableNameMethod",null);
            String tableName = (String) getTableNameMethod.invoke(null,null);
            c = db.rawQuery("SELECT  * FROM " + tableName + " WHERE " + columnNameSync +  " = " + syncFlag.toString(),null);
            return c;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        return c;
    }

    public Cursor selectAllDirtyObjectsForClass(Class sClass)
    {
        return selectObjectsOfClassWithSyncFlag(sClass, Constants.SYNC_STATUS.DIRTY.getValue());
    }

    public Cursor selectAllInsertedObjectsForClass(Class sClass)
    {
        return selectObjectsOfClassWithSyncFlag(sClass, Constants.SYNC_STATUS.INSERTED.getValue());
    }

    public Cursor selectAllDeletedObjectsForClass(Class sClass)
    {
        return selectObjectsOfClassWithSyncFlag(sClass, Constants.SYNC_STATUS.DELETED.getValue());
    }

    public Cursor selectAllConflictedObjectsForClass(Class sClass)
    {
        return selectObjectsOfClassWithSyncFlag(sClass, Constants.SYNC_STATUS.CONFLICTED.getValue());
    }

    public Cursor selectAllObjectsToDisplay(Class sClass)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        try {

            Method columnNameForSyncFlag = sClass.getDeclaredMethod("columnNameForSyncFlag",null);
            String columnNameSync = (String) columnNameForSyncFlag.invoke(null,null);

            Method getTableNameMethod = sClass.getDeclaredMethod("SQLITETableNameMethod",null);
            String tableName = (String) getTableNameMethod.invoke(null,null);
            c = db.rawQuery("SELECT  * FROM " + tableName + " WHERE " + columnNameSync +  " != 2 " ,null);
            return c;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }

        return c;
    }

}
