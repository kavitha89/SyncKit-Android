package com.example.android.network.sync.basicsyncadapter.provider;

import android.content.ContentValues;
import android.content.Context;
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

    private final String TRANSFORMERS_SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Transformer.TABLE_NAME + " ( client_id" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Transformer.KEY_TRANSFORMER_ID + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_NAME + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_LOCATION + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_MAKE + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_CURRENT_TEMP + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_OIL_LEVEL + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_OPERATING_POWER + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_WINDING_COUNT + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_WINDING_MAKE + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_TYPE + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_LAST_UPDATED_TIME + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_LAST_SERVER_SYNC_DATE + TYPE_TEXT + COMMA_SEP +
                    Transformer.KEY_SYNC_STATUS + TYPE_INTEGER + ")";

    /** SQL statement to drop "entry" table. */
    private final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Transformer.TABLE_NAME;

    public SyncDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create all tables
        db.execSQL(TRANSFORMERS_SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
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

}
