package com.example.android.network.sync.basicsyncadapter.models;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import com.example.android.network.sync.basicsyncadapter.provider.SyncDatabaseHelper;
import com.example.android.network.sync.basicsyncadapter.util.Constants;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Veni on 11/10/14.
 */
public class Turbine extends SyncModel implements Parcelable{

    public Date lastServerSyncDate;
    public Date lastUpdatedDate;
    public int syncStatus;
    public String trbLocation;
    public String trbName;
    public String trbCapacity;
    public String trbTemp;
    public String trbPressure;
    public String trbCurrentRPM;
    public String trbOilLevel;
    public String trbCompressorHealth;
    public String trbRotationSpeed;
    public String trbRotorCount;
    public String turbineID;

    public Turbine(JSONObject jsonObject)
    {

        try {
            this.trbCurrentRPM = jsonObject.getString("turbineCurrentRPM");
            this.trbRotationSpeed = jsonObject.getString("trubineCurrentRotationSpeed");
            this.trbCompressorHealth = jsonObject.getString("turbineCompressorHealth");
            this.trbLocation = jsonObject.getString("turbineLocation");
            this.trbName = jsonObject.getString("turbineName");
            this.trbOilLevel = jsonObject.getString("turbineOilLevel");
            this.trbPressure = jsonObject.getString("turbinePressure");
            this.trbRotorCount = jsonObject.getString("turbineRotorCount");
            this.trbTemp = jsonObject.getString("turbineTemperature");
            this.turbineID = jsonObject.getString("objectId");
            this.trbCapacity = jsonObject.getString("turbineCapacity");
            this.lastServerSyncDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
            this.lastUpdatedDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
        }

        catch (Exception ex)
        {
            Log.e("Turbine Model", "Failed to parse JSON due to: " + ex);
        }
    }


    protected Turbine(Parcel in) {
        client_id = in.readInt();
        long tmpLastServerSyncDate = in.readLong();
        lastServerSyncDate = tmpLastServerSyncDate != -1 ? new Date(tmpLastServerSyncDate) : null;
        long tmpLastUpdatedDate = in.readLong();
        lastUpdatedDate = tmpLastUpdatedDate != -1 ? new Date(tmpLastUpdatedDate) : null;
        syncStatus = in.readInt();
        trbLocation = in.readString();
        trbName = in.readString();
        trbCapacity = in.readString();
        trbTemp = in.readString();
        trbPressure = in.readString();
        trbCurrentRPM = in.readString();
        trbOilLevel = in.readString();
        trbCompressorHealth = in.readString();
        trbRotationSpeed = in.readString();
        trbRotorCount = in.readString();
        turbineID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(client_id);
        dest.writeLong(lastServerSyncDate != null ? lastServerSyncDate.getTime() : -1L);
        dest.writeLong(lastUpdatedDate != null ? lastUpdatedDate.getTime() : -1L);
        dest.writeInt(syncStatus);
        dest.writeString(trbLocation);
        dest.writeString(trbName);
        dest.writeString(trbCapacity);
        dest.writeString(trbTemp);
        dest.writeString(trbPressure);
        dest.writeString(trbCurrentRPM);
        dest.writeString(trbOilLevel);
        dest.writeString(trbCompressorHealth);
        dest.writeString(trbRotationSpeed);
        dest.writeString(trbRotorCount);
        dest.writeString(turbineID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Turbine> CREATOR = new Parcelable.Creator<Turbine>() {
        @Override
        public Turbine createFromParcel(Parcel in) {
            return new Turbine(in);
        }

        @Override
        public Turbine[] newArray(int size) {
            return new Turbine[size];
        }
    };

    public static String TABLE_NAME = "turbines";
    public static String KEY_TURBINE_CURRENT_ROTATION_SPEED = "trubineCurrentRotationSpeed";
    public static String KEY_TURBINE_CAPACITY = "turbineCapacity";
    public static String KEY_TURBINE_CURRENT_RPM = "turbineCurrentRPM";
    public static String KEY_TURBINE_COMP_HEALTH = "turbineCompressorHealth";
    public static String KEY_TURBINE_LOCATION = "turbineLocation";
    public static String KEY_TURBINE_NAME = "turbineName";
    public static String KEY_TURBINE_OIL_LEVEL = "turbineOilLevel";
    public static String KEY_TURBINE_PRESSURE = "turbinePressure";
    public static String KEY_TURBINE_ROTOR_COUNT = "turbineRotorCount";
    public static String KEY_TURBINE_TEMP = "turbineTemperature";
    public static String KEY_TURBINE_ID = "objectId";
    public static String KEY_SYNC_STATUS = "SyncStatus";
    public static String KEY_LAST_UPDATED_TIME = "lastUpdatedTime";
    public static String KEY_LAST_SERVER_SYNC_DATE = "lastServerSyncTime";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String BOILER_SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ( client_id" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_TURBINE_ID + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_NAME + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_CURRENT_ROTATION_SPEED + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_CAPACITY + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_CURRENT_RPM + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_COMP_HEALTH + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_LOCATION + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_OIL_LEVEL + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_PRESSURE + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_ROTOR_COUNT + TYPE_TEXT + COMMA_SEP +
                    KEY_TURBINE_TEMP + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_UPDATED_TIME + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_SERVER_SYNC_DATE + TYPE_TEXT + COMMA_SEP +
                    KEY_SYNC_STATUS + TYPE_INTEGER + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Turbine.TABLE_NAME;

    private static final String FETCH_ALL_URL = "https://api.parse.com/1/classes/Turbine";

    private static final String NEW_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Turbine";

    private static final String UPDATE_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Turbine";

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

    public static String createTableQuery(){return BOILER_SQL_CREATE_ENTRIES; }

    public static String deleteTableQuery(){return SQL_DELETE_ENTRIES; }

    public ContentValues contentValuesForInsert()
    {

        ContentValues values = new ContentValues();
        values.put(KEY_TURBINE_NAME,this.trbName);
        values.put(KEY_TURBINE_LOCATION, this.trbLocation);
        values.put(KEY_TURBINE_COMP_HEALTH, this.trbCompressorHealth);
        values.put(KEY_TURBINE_OIL_LEVEL, this.trbOilLevel);
        values.put(KEY_TURBINE_PRESSURE, this.trbPressure);
        values.put(KEY_TURBINE_ROTOR_COUNT, this.trbRotorCount);
        values.put(KEY_TURBINE_TEMP, this.trbTemp);
        values.put(KEY_TURBINE_CURRENT_RPM, this.trbCurrentRPM);
        values.put(KEY_TURBINE_CAPACITY, this.trbCapacity);
        values.put(KEY_TURBINE_CURRENT_ROTATION_SPEED, this.trbRotationSpeed);

        if(this.turbineID != null)
            values.put(KEY_TURBINE_ID, this.turbineID);

        if(this.lastServerSyncDate != null)
            values.put(KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(this.lastServerSyncDate));

        if(this.lastUpdatedDate != null)
            values.put(KEY_LAST_UPDATED_TIME, dateFormatter.format(this.lastUpdatedDate));
        values.put(KEY_SYNC_STATUS,this.syncStatus);

        return values;
    }

    public ContentValues contentValuesForUpdate()
    {
        ContentValues values = new ContentValues();
        values.put(KEY_TURBINE_NAME,this.trbName);
        values.put(KEY_TURBINE_LOCATION, this.trbLocation);
        values.put(KEY_TURBINE_COMP_HEALTH, this.trbCompressorHealth);
        values.put(KEY_TURBINE_OIL_LEVEL, this.trbOilLevel);
        values.put(KEY_TURBINE_PRESSURE, this.trbPressure);
        values.put(KEY_TURBINE_ROTOR_COUNT, this.trbRotorCount);
        values.put(KEY_TURBINE_TEMP, this.trbTemp);
        values.put(KEY_TURBINE_CURRENT_RPM, this.trbCurrentRPM);
        values.put(KEY_TURBINE_CAPACITY, this.trbCapacity);
        values.put(KEY_TURBINE_CURRENT_ROTATION_SPEED, this.trbRotationSpeed);

        if(this.turbineID != null)
            values.put(KEY_TURBINE_ID, this.turbineID);

        if(this.lastServerSyncDate != null)
            values.put(KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(this.lastServerSyncDate));

        if(this.lastUpdatedDate != null)
            values.put(KEY_LAST_UPDATED_TIME, dateFormatter.format(this.lastUpdatedDate));
        values.put(KEY_SYNC_STATUS,this.syncStatus);

        return values;
    }

    public int identificationAttributeValue()
    {
        return this.client_id;
    }

    public String urlForUpdateServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.turbineID; }

    public String urlForDeleteServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.turbineID; }

    public String postBodyForNewServerObject()
    {
        try {

            JSONObject newServerObjectJSON = new JSONObject();
            newServerObjectJSON.put("trubineCurrentRotationSpeed", this.trbRotationSpeed);
            newServerObjectJSON.put("turbineCompressorHealth", this.trbCompressorHealth);
            newServerObjectJSON.put("turbineLocation", this.trbLocation);
            newServerObjectJSON.put("turbineName", this.trbName);
            newServerObjectJSON.put("turbineOilLevel", this.trbOilLevel);
            newServerObjectJSON.put("turbinePressure", this.trbPressure);
            newServerObjectJSON.put("turbineRotorCount", this.trbRotorCount);
            newServerObjectJSON.put("turbineTemperature", this.trbTemp);
            newServerObjectJSON.put("turbineCurrentRPM", this.trbCurrentRPM);
            newServerObjectJSON.put("turbineCapacity", this.trbCapacity);

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
            newServerObjectJSON.put("trubineCurrentRotationSpeed", this.trbRotationSpeed);
            newServerObjectJSON.put("turbineCompressorHealth", this.trbCompressorHealth);
            newServerObjectJSON.put("turbineLocation", this.trbLocation);
            newServerObjectJSON.put("turbineName", this.trbName);
            newServerObjectJSON.put("turbineOilLevel", this.trbOilLevel);
            newServerObjectJSON.put("turbinePressure", this.trbPressure);
            newServerObjectJSON.put("turbineRotorCount", this.trbRotorCount);
            newServerObjectJSON.put("turbineTemperature", this.trbTemp);
            newServerObjectJSON.put("turbineCurrentRPM", this.trbCurrentRPM);
            newServerObjectJSON.put("turbineCapacity", this.trbCapacity);

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

    public String handleObjectDeleteResponseFromServer(String response,Context context)
    {
        try {

            SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
            dbHelper.deletObject(this);
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
                this.turbineID = updateResponse.getString("objectId");
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

    public static ArrayList<Turbine> fetchAllAvailableObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllObjectsToDisplay(Turbine.class));
    }

    public static ArrayList<Turbine>fetchAllDirtyObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllDirtyObjectsForClass(Turbine.class));
    }

    public static ArrayList<Turbine>fetchAllNewObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllInsertedObjectsForClass(Turbine.class));
    }

    public static ArrayList<Turbine>fetchAllDeletedObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllDeletedObjectsForClass(Turbine.class));
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

    private static ArrayList<Turbine> generateArrayFromCursor(Cursor c)
    {

        ArrayList<Turbine> turbines = new ArrayList<Turbine>();

        try {
            while (c.moveToNext()) {
                Turbine turbine = new Turbine();

                turbine.client_id = c.getInt(0);
                turbine.turbineID = c.getString(1);
                turbine.trbName = c.getString(2);
                turbine.trbRotationSpeed = c.getString(3);
                turbine.trbCapacity = c.getString(4);
                turbine.trbCurrentRPM = c.getString(5);
                turbine.trbCompressorHealth = c.getString(6);
                turbine.trbLocation = c.getString(7);
                turbine.trbOilLevel = c.getString(8);
                turbine.trbPressure = c.getString(9);
                turbine.trbRotorCount = c.getString(10);
                turbine.trbTemp = c.getString(11);
                turbine.syncStatus = c.getInt(14);

                if (!c.isNull(12)) {

                    if(c.getString(12) != null) {
                        Date date = dateFormatter.parse(c.getString(12));
                        turbine.lastUpdatedDate = date;
                    }

                }

                if (!c.isNull(13)) {

                    if(c.getString(13) != null) {

                        Date date = dateFormatter.parse(c.getString(13));
                        turbine.lastServerSyncDate = date;
                    }

                }

                turbines.add(turbine);
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return turbines;
    }

    public boolean deleteObject(Context context)
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
    public boolean insertObjectToDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        this.syncStatus = Constants.SYNC_STATUS.INSERTED.getValue();
        return dbHelper.insertObject(this);
    }

    public boolean commitObjectToDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }

    public boolean updateObjectInDB(Context context)
    {
        this.lastUpdatedDate = new Date();
        if(this.syncStatus == Constants.SYNC_STATUS.INSERTED.getValue()) {
            //keep it the same, don't do anything!
        }
        else
        {
            this.syncStatus = Constants.SYNC_STATUS.DIRTY.getValue();
        }
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return dbHelper.udpateObject(this);
    }


    private static boolean isTransformerObjectWithIDExistsInArray(ArrayList<Turbine> turbines,String turbineID)
    {
        boolean isExists =false;

        for (Turbine e : turbines) {

            if(e.turbineID.equals(turbineID))
            {
                isExists = true;
                break;
            }
        }

        return isExists;
    }

    private static int getSyncStatusForObjectWithID(ArrayList<Turbine> turbines,String turbineID)
    {
        int sync_flag = -1;

        for (Turbine e : turbines) {

            if(e.turbineID.equals(turbineID))
            {
                sync_flag = e.syncStatus;
                break;
            }
        }

        return sync_flag;
    }

    private static int getClientIDForObjectWithID(ArrayList<Turbine> turbines,String turbineID)
    {
        int sync_flag = 0;

        for (Turbine e : turbines) {

            if(e.turbineID.equals(turbineID))
            {
                sync_flag = e.client_id;
                break;
            }
        }

        return sync_flag;
    }

    public static SyncResult handleInsertWithData(Context contentResolver,InputStream stream,SyncResult syncResult) throws RemoteException, OperationApplicationException {

        final List<Turbine> transformersListFromResponse = parseTurbinesResponse(stream);

        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(contentResolver);

        ArrayList<Turbine> turbineListFromDB = generateArrayFromCursor(dbHelper.selectAllObjectsOfClass(Turbine.class));


        for (Turbine e : transformersListFromResponse) {

            //entryMap.put(e.transformerID, e);
            if (isTransformerObjectWithIDExistsInArray(turbineListFromDB, e.turbineID)) {
                if (getSyncStatusForObjectWithID(turbineListFromDB, e.turbineID) == Constants.SYNC_STATUS.SYNCED.getValue()) {
                    e.client_id = getClientIDForObjectWithID(turbineListFromDB,e.turbineID);
                    dbHelper.udpateObject(e);
                }

            } else {
                dbHelper.insertObject(e);
            }
        }
        return syncResult;
    }

    private static ArrayList<Turbine> parseTurbinesResponse(InputStream stream)
    {
        ArrayList<Turbine> turbineArrayList = new ArrayList<Turbine>();

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
                Turbine turbineObject = new Turbine(ja.getJSONObject(i));
                turbineArrayList.add(turbineObject);
            }
        }
        catch (Exception ex) {
            Log.e("Turbine Model", "Failed to parse JSON due to: " + ex);
        }

        return turbineArrayList;
    }


    public  Turbine()
    {
        this.syncStatus = -1;
    }
}
