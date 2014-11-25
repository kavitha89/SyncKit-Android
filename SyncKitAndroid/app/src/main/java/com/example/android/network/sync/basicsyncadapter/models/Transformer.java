package com.example.android.network.sync.basicsyncadapter.models;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import com.example.android.network.sync.basicsyncadapter.provider.FeedProvider;
import com.example.android.network.sync.basicsyncadapter.provider.SyncDatabaseHelper;
import com.example.android.network.sync.basicsyncadapter.util.Constants;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Veni on 11/10/14.
 */
public class Transformer extends SyncModel implements Parcelable{

    public static final String TAG = "Transformer Model";

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.network.sync.basicsyncadapter";

    /**
     * Base URI. (content://com.example.android.network.sync.basicsyncadapter)
     */

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ENTRIES = "transformers";

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.basicsyncadapter.transformers";
    /**
     * MIME type for individual entries.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.basicsyncadapter.transformers";


    public static String TABLE_NAME = "transformers";
    public static String KEY_NAME = "trsName";
    public static String KEY_LOCATION = "trsLocation";
    public static String KEY_CURRENT_TEMP = "transformerCurrentTemperature";
    public static String KEY_OIL_LEVEL = "transformerOilLevel";
    public static String KEY_OPERATING_POWER = "transformerOperatingPower";
    public static String KEY_MAKE = "transformerMake";
    public static String KEY_TYPE = "transformerType";
    public static String KEY_WINDING_COUNT = "transformerWindingCount";
    public static String KEY_WINDING_MAKE = "transformerWindingMake";
    public static String KEY_TRANSFORMER_ID = "objectId";
    public static String KEY_SYNC_STATUS = "SyncStatus";
    public static String KEY_LAST_UPDATED_TIME = "lastUpdatedTime";
    public static String KEY_LAST_SERVER_SYNC_DATE = "lastServerSyncTime";

    public static final String[] PROJECTION = {
            Transformer.KEY_TRANSFORMER_ID,
            Transformer.KEY_NAME,
            Transformer.KEY_LOCATION,
            Transformer.KEY_MAKE,
            Transformer.KEY_CURRENT_TEMP,
            Transformer.KEY_OIL_LEVEL,
            Transformer.KEY_OPERATING_POWER,
            Transformer.KEY_WINDING_COUNT,
            Transformer.KEY_WINDING_MAKE,
            Transformer.KEY_TYPE,
            Transformer.KEY_LAST_UPDATED_TIME,
            Transformer.KEY_LAST_SERVER_SYNC_DATE,
            Transformer.KEY_SYNC_STATUS
    };

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String TRANSFORMERS_SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ( client_id" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_TRANSFORMER_ID + TYPE_TEXT + COMMA_SEP +
                    KEY_NAME + TYPE_TEXT + COMMA_SEP +
                    KEY_LOCATION + TYPE_TEXT + COMMA_SEP +
                    KEY_MAKE + TYPE_TEXT + COMMA_SEP +
                    KEY_CURRENT_TEMP + TYPE_TEXT + COMMA_SEP +
                    KEY_OIL_LEVEL + TYPE_TEXT + COMMA_SEP +
                    KEY_OPERATING_POWER + TYPE_TEXT + COMMA_SEP +
                    KEY_WINDING_COUNT + TYPE_TEXT + COMMA_SEP +
                    KEY_WINDING_MAKE + TYPE_TEXT + COMMA_SEP +
                    KEY_TYPE + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_UPDATED_TIME + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_SERVER_SYNC_DATE + TYPE_TEXT + COMMA_SEP +
                    KEY_SYNC_STATUS + TYPE_INTEGER + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Transformer.TABLE_NAME;

    private static final String FETCH_ALL_URL = "https://api.parse.com/1/classes/Transformer";

    private static final String NEW_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Transformer";

    private static final String UPDATE_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Transformer";

    //2014-11-10T13:17:21.210Z
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    public static String urlForFetchAll()
    {
        return FETCH_ALL_URL;
    }

    public static String urlForDeltaFetch()
    {
        return FETCH_ALL_URL;
    }

    public static String queryForDeltaFetch()
    {
        return null;
    }

    public static String columnNameForSyncFlag()
    {
        return KEY_SYNC_STATUS;
    }

    public static String columnNameForLastServerSyncDate()
    {
        return KEY_LAST_SERVER_SYNC_DATE;
    }

    public static String columnNameForLastUpdatedDate()
    {
        return KEY_LAST_UPDATED_TIME;
    }

    public static String urlForNewServerObject(){return NEW_SERVER_OBJECT_URL; }

    public static String SQLITETableNameMethod(){ return  TABLE_NAME;}

    public static String createTableQuery(){return TRANSFORMERS_SQL_CREATE_ENTRIES; }

    public static String deleteTableQuery(){return SQL_DELETE_ENTRIES; }


    public ContentValues contentValuesForInsert()
    {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,this.trsName);
        values.put(KEY_LOCATION, this.trsLocation);
        values.put(KEY_CURRENT_TEMP, this.trsCurrentTemp);
        values.put(KEY_MAKE, this.trsMake);
        values.put(KEY_OIL_LEVEL, this.trsOilLevel);
        values.put(KEY_OPERATING_POWER, this.trsOperatingPower);
        values.put(KEY_TYPE, this.trsType);
        values.put(KEY_WINDING_COUNT, this.trsWindingCount);
        values.put(KEY_WINDING_MAKE, this.trsWindingMake);
        if(this.transformerID != null)
            values.put(KEY_TRANSFORMER_ID, this.transformerID);

        if(this.lastServerSyncDate != null)
            values.put(KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(this.lastServerSyncDate));

        if(this.lastUpdatedDate != null)
            values.put(KEY_LAST_UPDATED_TIME, dateFormatter.format(this.lastUpdatedDate));
        values.put(KEY_SYNC_STATUS,0);

        return values;
    }

    public ContentValues contentValuesForUpdate()
    {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,this.trsName);
        values.put(KEY_LOCATION, this.trsLocation);
        values.put(KEY_CURRENT_TEMP, this.trsCurrentTemp);
        values.put(KEY_MAKE, this.trsMake);
        values.put(KEY_OIL_LEVEL, this.trsOilLevel);
        values.put(KEY_OPERATING_POWER, this.trsOperatingPower);
        values.put(KEY_TYPE, this.trsType);
        values.put(KEY_WINDING_COUNT, this.trsWindingCount);
        values.put(KEY_WINDING_MAKE, this.trsWindingMake);
        if(this.transformerID != null)
            values.put(KEY_TRANSFORMER_ID, this.transformerID);

        if(this.lastServerSyncDate != null)
            values.put(KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(this.lastServerSyncDate));

        if(this.lastUpdatedDate != null)
            values.put(KEY_LAST_UPDATED_TIME, dateFormatter.format(this.lastUpdatedDate));
        values.put(KEY_SYNC_STATUS,0);

        return values;
    }

    public int identificationAttributeValue()
    {
        return this.client_id;
    }


    public String urlForUpdateServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.transformerID; }

    public String urlForDeleteServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.transformerID; }

    public String postBodyForNewServerObject()
    {
        try {

            JSONObject newServerObjectJSON = new JSONObject();
            newServerObjectJSON.put("transformerNickName", this.trsName);
            newServerObjectJSON.put("transformerLocation", this.trsLocation);
            newServerObjectJSON.put("transformerCurrentTemperature", this.trsCurrentTemp);
            newServerObjectJSON.put("transformerMake", this.trsMake);
            newServerObjectJSON.put("transformerOilLevel", this.trsOilLevel);
            newServerObjectJSON.put("transformerOperatingPower", this.trsOperatingPower);
            newServerObjectJSON.put("transformerType", this.trsType);
            newServerObjectJSON.put("transformerWindingCount", this.trsWindingCount);
            newServerObjectJSON.put("transformerWindingMake", this.trsWindingMake);

            return newServerObjectJSON.toString();

        }

        catch (Exception ex)
        {

        }

        return "";
    }

    public String postBodyForUpdateServerObject()
    {
        try {

            JSONObject newServerObjectJSON = new JSONObject();
            newServerObjectJSON.put("transformerNickName", this.trsName);
            newServerObjectJSON.put("transformerLocation", this.trsLocation);
            newServerObjectJSON.put("transformerCurrentTemperature", this.trsCurrentTemp);
            newServerObjectJSON.put("transformerMake", this.trsMake);
            newServerObjectJSON.put("transformerOilLevel", this.trsOilLevel);
            newServerObjectJSON.put("transformerOperatingPower", this.trsOperatingPower);
            newServerObjectJSON.put("transformerType", this.trsType);
            newServerObjectJSON.put("transformerWindingCount", this.trsWindingCount);
            newServerObjectJSON.put("transformerWindingMake", this.trsWindingMake);

            return newServerObjectJSON.toString();

        }

        catch (Exception ex)
        {

        }
        return "";
    }

    public String handleObjectUpdateResponseFromServer(String response,Context context)
    {
        try {
            JSONObject updateResponse = new JSONObject(response);
            if(updateResponse.has("updatedAt"))
            {
                //update row in db - set id and lastUpdatedDate
                this.lastServerSyncDate = dateFormatter.parse(updateResponse.getString("updatedAt"));
                this.lastUpdatedDate = dateFormatter.parse(updateResponse.getString("updatedAt"));
                this.syncStatus = Constants.SYNC_STATUS.SYNCED.getValue();
                if(this.commitObjectToDB(context))
                    return "1";
                //set sync status to Synced
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        return "0";

    }

    public String handleObjectCreateResponseFromServer(String response,Context context)
    {
        try {
            JSONObject updateResponse = new JSONObject(response);
            if(updateResponse.has("objectId"))
            {
                //update row in db, set lastUpdated date.
                this.transformerID = updateResponse.getString("objectId");
                //set sync status to synced
                this.syncStatus = Constants.SYNC_STATUS.SYNCED.getValue();
                this.lastServerSyncDate = dateFormatter.parse(updateResponse.getString("createdAt"));
                this.lastUpdatedDate = dateFormatter.parse(updateResponse.getString("createdAt"));
                if(this.commitObjectToDB(context))
                    return "1";
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        return "0";
    }

    public static ArrayList<Transformer>fetchAllTransformerObjectsInDB(ContentResolver resolver)
    {
        try {

            final ContentResolver contentResolver = resolver;

            Uri uri = Transformer.CONTENT_URI; // Get all entries

            String[] mProjection = {
                    Transformer.KEY_TRANSFORMER_ID,
                    Transformer.KEY_NAME,
                    Transformer.KEY_LOCATION,
                    Transformer.KEY_MAKE,
                    Transformer.KEY_CURRENT_TEMP,
                    Transformer.KEY_OIL_LEVEL,
                    Transformer.KEY_OPERATING_POWER,
                    Transformer.KEY_WINDING_COUNT,
                    Transformer.KEY_WINDING_MAKE,
                    Transformer.KEY_TYPE,
                    Transformer.KEY_LAST_UPDATED_TIME,
                    Transformer.KEY_LAST_SERVER_SYNC_DATE,
                    Transformer.KEY_SYNC_STATUS
            };

            Cursor c = contentResolver.query(uri, mProjection, null, null, null);

            return generateArrayFromCursor(c);
        }
        catch (Exception Ex)
        {
            Log.i(TAG,Ex.toString());
        }

        return null;
    }

    public static ArrayList<Transformer>fetchAllAvailableObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllObjectsToDisplay(Transformer.class));
    }

    public static ArrayList<Transformer>fetchAllDirtyObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllConflictedObjectsForClass(Transformer.class));
    }

    public static ArrayList<Transformer>fetchAllNewObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllInsertedObjectsForClass(Transformer.class));
    }

    public static ArrayList<Transformer>fetchAllDeletedObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllDeletedObjectsForClass(Transformer.class));
    }

    public boolean setObjectAsSynced(Context context)
    {
        this.syncStatus = Constants.SYNC_STATUS.SYNCED.getValue();
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }

    public boolean setObjectAsDirty(Context context)
    {
        this.syncStatus = Constants.SYNC_STATUS.DIRTY.getValue();
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }

    public boolean setObjectAsConflicted(Context context)
    {
        this.syncStatus = Constants.SYNC_STATUS.CONFLICTED.getValue();
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }

    public boolean setObjectAsNew(Context context)
    {
        this.syncStatus = Constants.SYNC_STATUS.INSERTED.getValue();
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }


    public static ArrayList<Transformer>fetchAllTransformerObjectsInDBWithSyncStatus(ContentResolver resolver,int syncStatus)
    {
        ArrayList<Transformer> transformers = new ArrayList<Transformer>();

        try {

            final ContentResolver contentResolver = resolver;

            Uri uri = Transformer.CONTENT_URI; // Get all entries

            String mSelectionClause = Transformer.KEY_SYNC_STATUS + " = ?";

            String[] mSelectionArgs = {""};

            mSelectionArgs[0] = Integer.toString(syncStatus);

            String[] mProjection = {
                    Transformer.KEY_TRANSFORMER_ID,
                    Transformer.KEY_NAME,
                    Transformer.KEY_LOCATION,
                    Transformer.KEY_MAKE,
                    Transformer.KEY_CURRENT_TEMP,
                    Transformer.KEY_OIL_LEVEL,
                    Transformer.KEY_OPERATING_POWER,
                    Transformer.KEY_WINDING_COUNT,
                    Transformer.KEY_WINDING_MAKE,
                    Transformer.KEY_TYPE,
                    Transformer.KEY_LAST_UPDATED_TIME,
                    Transformer.KEY_LAST_SERVER_SYNC_DATE,
                    Transformer.KEY_SYNC_STATUS
            };

            Cursor c = contentResolver.query(uri, mProjection, mSelectionClause, mSelectionArgs, null);

            return generateArrayFromCursor(c);
        }
        catch (Exception Ex)
        {
            Log.i(TAG,Ex.toString());
        }

        return null;
    }

    private static ArrayList<Transformer> generateArrayFromCursor(Cursor c)
    {

        ArrayList<Transformer> transformers = new ArrayList<Transformer>();

        try {
            while (c.moveToNext()) {
                Transformer transformer = new Transformer();

                transformer.client_id = c.getInt(0);
                transformer.transformerID = c.getString(1);
                transformer.trsName = c.getString(2);
                transformer.trsLocation = c.getString(3);
                transformer.trsMake = c.getString(4);
                transformer.trsCurrentTemp = c.getString(5);
                transformer.trsOilLevel = c.getString(6);
                transformer.trsOperatingPower = c.getString(7);
                transformer.trsWindingCount = c.getString(8);
                transformer.trsWindingMake = c.getString(9);
                transformer.trsType = c.getString(10);
                transformer.syncStatus = c.getInt(13);


                /* TODO read as dates. */

                if (!c.isNull(11)) {

                    if(c.getString(11) != null) {
                        Date date = dateFormatter.parse(c.getString(11));
                        transformer.lastUpdatedDate = date;
                    }

                }

                if (!c.isNull(12)) {

                    if(c.getString(12) != null) {

                        Date date = dateFormatter.parse(c.getString(12));
                        transformer.lastServerSyncDate = date;
                    }

                }

                transformers.add(transformer);
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
            return transformers;
    }

    public boolean deleteTransformerObject(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);

        if(this.syncStatus == Constants.SYNC_STATUS.INSERTED.getValue()) {
            return dbHelper.deletObject(this);
        }
        else
        {
            this.syncStatus = Constants.SYNC_STATUS.DELETED.getValue();
            return dbHelper.udpateObject(this);
        }

    }
    public boolean saveTransformerObjectToDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.insertObject(this);
    }

    public boolean commitObjectToDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }

    public boolean updateTransformerObjectInDB(Context context)
    {
        this.lastUpdatedDate = new Date();
        this.syncStatus = Constants.SYNC_STATUS.DIRTY.getValue();
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }


    private static boolean isTransformerObjectWithIDExistsInArray(ArrayList<Transformer> transformers,String transformerID)
    {
        boolean isExists =false;

        for (Transformer e : transformers) {

            if(e.transformerID.equals(transformerID))
            {
                isExists = true;
                break;
            }
        }

        return isExists;
    }

    private static int getSyncStatusForObjectWithID(ArrayList<Transformer> transformers,String transformerID)
    {
        int sync_flag = -1;

        for (Transformer e : transformers) {

            if(e.transformerID.equals(transformerID))
            {
                sync_flag = e.syncStatus;
                break;
            }
        }

        return sync_flag;
    }

    public static SyncResult handleInsertWithData(Context contentResolver,InputStream stream,SyncResult syncResult) throws RemoteException, OperationApplicationException {

        final List<Transformer> transformersListFromResponse = parseTransformersResponse(stream);

        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(contentResolver);

        ArrayList<Transformer> transformerListFromDB = generateArrayFromCursor(dbHelper.selectAllObjectsOfClass(Transformer.class));

        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();


        for (Transformer e : transformersListFromResponse) {

            //entryMap.put(e.transformerID, e);
            if (isTransformerObjectWithIDExistsInArray(transformerListFromDB, e.transformerID)) {
                if (getSyncStatusForObjectWithID(transformerListFromDB, e.transformerID) == Constants.SYNC_STATUS.SYNCED.getValue()) {
                    dbHelper.udpateObject(e);
                }

            } else {
                dbHelper.insertObject(e);
            }
        }
        return syncResult;
    }

    private static ArrayList<Transformer> parseTransformersResponse(InputStream stream)
    {
        ArrayList<Transformer> transformerArrayList = new ArrayList<Transformer>();

        try
        {
            StringBuilder builder = new StringBuilder();
            BufferedReader b_reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while((line = b_reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject jso = new JSONObject(builder.toString());
            JSONArray ja = jso.getJSONArray("results");

            for( int i = 0; i < ja.length(); i++ ) {
                Transformer transformerObject = new Transformer(ja.getJSONObject(i));
                transformerArrayList.add(transformerObject);
            }
        }
        catch (Exception ex) {
            Log.e("Transformer Model", "Failed to parse JSON due to: " + ex);
        }

        return transformerArrayList;
    }


    public  Transformer()
    {
        this.syncStatus = -1;
    }

    public Transformer(String name, String location) {
        this.trsName = name;
        this.trsLocation = location;
    }

    public Transformer(JSONObject jsonObject)
    {
        try {
            this.trsName = jsonObject.getString("transformerNickName");
            this.trsLocation = jsonObject.getString("transformerLocation");
            this.trsCurrentTemp = jsonObject.getString("transformerCurrentTemperature");
            this.trsOilLevel = jsonObject.getString("transformerOilLevel");
            this.trsOperatingPower = jsonObject.getString("transformerOperatingPower");
            this.trsMake = jsonObject.getString("transformerMake");
            this.trsType = jsonObject.getString("transformerType");
            this.trsWindingCount = jsonObject.getString("transformerWindingCount");
            this.trsWindingMake = jsonObject.getString("transformerWindingMake");
            this.transformerID = jsonObject.getString("objectId");
            this.lastServerSyncDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
            this.lastUpdatedDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
        }

        catch (Exception ex)
        {
            Log.e("Transformers Model", "Failed to parse JSON due to: " + ex);
        }
    }

    @SerializedName("updatedAt")
    public Date lastServerSyncDate;

    //additional values for sync
    public Date lastUpdatedDate;
    public int syncStatus;

    @SerializedName("transformerLocation")
    public String trsLocation;
    @SerializedName("transformerNickName")
    public String trsName;
    @SerializedName("transformerOperatingPower")
    public String trsOperatingPower;
    @SerializedName("transformerOilLevel")
    public String trsOilLevel;
    @SerializedName("transformerWindingCount")
    public String trsWindingCount;
    @SerializedName("transformerMake")
    public String trsMake;
    @SerializedName("transformerWindingMake")
    public String trsWindingMake;
    @SerializedName("transformerCurrentTemperature")
    public String trsCurrentTemp;
    @SerializedName("transformerType")
    public String trsType;
    @SerializedName("objectId")
    public String transformerID;

    protected Transformer(Parcel in) {
        long tmpLastServerSyncDate = in.readLong();
        lastServerSyncDate = tmpLastServerSyncDate != -1 ? new Date(tmpLastServerSyncDate) : null;
        long tmpLastUpdatedDate = in.readLong();
        lastUpdatedDate = tmpLastUpdatedDate != -1 ? new Date(tmpLastUpdatedDate) : null;
        syncStatus = in.readInt();
        trsLocation = in.readString();
        trsName = in.readString();
        trsOperatingPower = in.readString();
        trsOilLevel = in.readString();
        trsWindingCount = in.readString();
        trsMake = in.readString();
        trsWindingMake = in.readString();
        trsCurrentTemp = in.readString();
        trsType = in.readString();
        transformerID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(lastServerSyncDate != null ? lastServerSyncDate.getTime() : -1L);
        dest.writeLong(lastUpdatedDate != null ? lastUpdatedDate.getTime() : -1L);
        dest.writeInt(syncStatus);
        dest.writeString(trsLocation);
        dest.writeString(trsName);
        dest.writeString(trsOperatingPower);
        dest.writeString(trsOilLevel);
        dest.writeString(trsWindingCount);
        dest.writeString(trsMake);
        dest.writeString(trsWindingMake);
        dest.writeString(trsCurrentTemp);
        dest.writeString(trsType);
        dest.writeString(transformerID);
    }

    public static final Parcelable.Creator<Transformer> CREATOR = new Parcelable.Creator<Transformer>() {
        @Override
        public Transformer createFromParcel(Parcel in) {
            return new Transformer(in);
        }

        @Override
        public Transformer[] newArray(int size) {
            return new Transformer[size];
        }
    };
}
