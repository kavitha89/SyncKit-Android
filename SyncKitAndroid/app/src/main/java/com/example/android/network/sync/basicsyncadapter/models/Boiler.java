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
public class Boiler extends SyncModel implements Parcelable{

    public static String TABLE_NAME = "boilers";
    public static String KEY_BOILER_NAME = "boilerName";
    public static String KEY_BOILER_CAPACITY = "boilerCapacity";
    public static String KEY_BOILER_LOCATION = "boilerLocation";
    public static String KEY_BOILER_MAKE = "boilerMake";
    public static String KEY_BOILER_TEMP = "boilerTemperature";
    public static String KEY_BOILER_PRESSURE = "boilerPressure";
    public static String KEY_BOILER_HEALTH_STATUS = "boilerHealthStatus";
    public static String KEY_BOILER_CURRENT_CONTAINMENT = "boilerCurrentContainment";
    public static String KEY_BOILER_ID = "objectId";
    public static String KEY_SYNC_STATUS = "SyncStatus";
    public static String KEY_LAST_UPDATED_TIME = "lastUpdatedTime";
    public static String KEY_LAST_SERVER_SYNC_DATE = "lastServerSyncTime";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String BOILER_SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ( client_id" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_BOILER_ID + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_NAME + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_CAPACITY + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_LOCATION + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_MAKE + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_TEMP + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_PRESSURE + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_HEALTH_STATUS + TYPE_TEXT + COMMA_SEP +
                    KEY_BOILER_CURRENT_CONTAINMENT + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_UPDATED_TIME + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_SERVER_SYNC_DATE + TYPE_TEXT + COMMA_SEP +
                    KEY_SYNC_STATUS + TYPE_INTEGER + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Boiler.TABLE_NAME;

    private static final String FETCH_ALL_URL = "https://api.parse.com/1/classes/Boiler";

    private static final String NEW_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Boiler";

    private static final String UPDATE_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Boiler";

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
        values.put(KEY_BOILER_NAME,this.bName);
        values.put(KEY_BOILER_LOCATION, this.bLocation);
        values.put(KEY_BOILER_CAPACITY, this.bCapacity);
        values.put(KEY_BOILER_CURRENT_CONTAINMENT, this.bCurrentContainment);
        values.put(KEY_BOILER_HEALTH_STATUS, this.bHealthStatus);
        values.put(KEY_BOILER_PRESSURE, this.bPressure);
        values.put(KEY_BOILER_MAKE, this.bMake);
        values.put(KEY_BOILER_TEMP, this.bTemp);
        if(this.boilerID != null)
            values.put(KEY_BOILER_ID, this.boilerID);

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
        values.put(KEY_BOILER_NAME,this.bName);
        values.put(KEY_BOILER_LOCATION, this.bLocation);
        values.put(KEY_BOILER_CAPACITY, this.bCapacity);
        values.put(KEY_BOILER_CURRENT_CONTAINMENT, this.bCurrentContainment);
        values.put(KEY_BOILER_HEALTH_STATUS, this.bHealthStatus);
        values.put(KEY_BOILER_PRESSURE, this.bPressure);
        values.put(KEY_BOILER_MAKE, this.bMake);
        values.put(KEY_BOILER_TEMP, this.bTemp);
        if(this.boilerID != null)
            values.put(KEY_BOILER_ID, this.boilerID);

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


    public String urlForUpdateServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.boilerID; }

    public String urlForDeleteServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.boilerID; }

    public String postBodyForNewServerObject()
    {
        try {

            JSONObject newServerObjectJSON = new JSONObject();
            newServerObjectJSON.put("boilerName", this.bName);
            newServerObjectJSON.put("boilerLocation", this.bLocation);
            newServerObjectJSON.put("boilerCapacity", this.bCapacity);
            newServerObjectJSON.put("boilerMake", this.bMake);
            newServerObjectJSON.put("boilerTemperature", this.bTemp);
            newServerObjectJSON.put("boilerPressure", this.bPressure);
            newServerObjectJSON.put("boilerHealthStatus", this.bHealthStatus);
            newServerObjectJSON.put("boilerCurrentContainment", this.bCurrentContainment);

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
            newServerObjectJSON.put("boilerName", this.bName);
            newServerObjectJSON.put("boilerLocation", this.bLocation);
            newServerObjectJSON.put("boilerCapacity", this.bCapacity);
            newServerObjectJSON.put("boilerMake", this.bMake);
            newServerObjectJSON.put("boilerTemperature", this.bTemp);
            newServerObjectJSON.put("boilerPressure", this.bPressure);
            newServerObjectJSON.put("boilerHealthStatus", this.bHealthStatus);
            newServerObjectJSON.put("boilerCurrentContainment", this.bCurrentContainment);

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
                this.boilerID = updateResponse.getString("objectId");
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

    public static ArrayList<Boiler> fetchAllAvailableObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllObjectsToDisplay(Boiler.class));
    }

    public static ArrayList<Boiler>fetchAllDirtyObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllDirtyObjectsForClass(Boiler.class));
    }

    public static ArrayList<Boiler>fetchAllNewObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllInsertedObjectsForClass(Boiler.class));
    }

    public static ArrayList<Boiler>fetchAllDeletedObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllDeletedObjectsForClass(Boiler.class));
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

    private static ArrayList<Boiler> generateArrayFromCursor(Cursor c)
    {

        ArrayList<Boiler> boilers = new ArrayList<Boiler>();

        try {
            while (c.moveToNext()) {
                Boiler boiler = new Boiler();

                boiler.client_id = c.getInt(0);
                boiler.boilerID = c.getString(1);
                boiler.bName = c.getString(2);
                boiler.bCapacity = c.getString(3);
                boiler.bLocation = c.getString(4);
                boiler.bMake = c.getString(5);
                boiler.bTemp = c.getString(6);
                boiler.bPressure = c.getString(7);
                boiler.bHealthStatus = c.getString(8);
                boiler.bCurrentContainment = c.getString(9);
                boiler.syncStatus = c.getInt(12);

                if (!c.isNull(10)) {
                    if(c.getString(10) != null) {
                        Date date = dateFormatter.parse(c.getString(10));
                        boiler.lastUpdatedDate = date;
                    }
                }

                if (!c.isNull(11)) {
                    if(c.getString(11) != null) {

                        Date date = dateFormatter.parse(c.getString(11));
                        boiler.lastServerSyncDate = date;
                    }
                }
                boilers.add(boiler);
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return boilers;
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

    private static boolean isBoilerObjectWithIDExistsInArray(ArrayList<Boiler> boilers,String boilerID)
    {
        boolean isExists =false;

        for (Boiler e : boilers) {

            if(e.boilerID.equals(boilerID))
            {
                isExists = true;
                break;
            }
        }

        return isExists;
    }

    private static int getSyncStatusForObjectWithID(ArrayList<Boiler> boilers,String boilerID)
    {
        int sync_flag = -1;

        for (Boiler e : boilers) {

            if(e.boilerID.equals(boilerID))
            {
                sync_flag = e.syncStatus;
                break;
            }
        }

        return sync_flag;
    }

    private static int getClientIDForObjectWithID(ArrayList<Boiler> boilers,String boilerID)
    {
        int sync_flag = 0;

        for (Boiler e : boilers) {

            if(e.boilerID.equals(boilerID))
            {
                sync_flag = e.client_id;
                break;
            }
        }

        return sync_flag;
    }

    public static SyncResult handleInsertWithData(Context contentResolver,InputStream stream,SyncResult syncResult) throws RemoteException, OperationApplicationException {

        final List<Boiler> boilersListFromResponse = parseBoilersResponse(stream);

        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(contentResolver);

        ArrayList<Boiler> boilerListFromDB = generateArrayFromCursor(dbHelper.selectAllObjectsOfClass(Boiler.class));


        for (Boiler e : boilersListFromResponse) {

            if (isBoilerObjectWithIDExistsInArray(boilerListFromDB, e.boilerID)) {
                if (getSyncStatusForObjectWithID(boilerListFromDB, e.boilerID) == Constants.SYNC_STATUS.SYNCED.getValue()) {
                    e.client_id = getClientIDForObjectWithID(boilerListFromDB,e.boilerID);
                    dbHelper.udpateObject(e);
                }

            } else {
                dbHelper.insertObject(e);
            }
        }
        return syncResult;
    }

    private static ArrayList<Boiler> parseBoilersResponse(InputStream stream)
    {
        ArrayList<Boiler> boilerArrayList = new ArrayList<Boiler>();

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
                Boiler boilerObject = new Boiler(ja.getJSONObject(i));
                boilerArrayList.add(boilerObject);
            }
        }
        catch (Exception ex) {
            Log.e("Boiler Model", "Failed to parse JSON due to: " + ex);
        }
        return boilerArrayList;
    }



    public Date lastServerSyncDate;
    public Date lastUpdatedDate;
    public int syncStatus;
    public String bName;
    public String bCapacity;
    public String bLocation;
    public String bMake;
    public String bTemp;
    public String bPressure;
    public String bHealthStatus;
    public String bCurrentContainment;
    public String boilerID;

    public Boiler()
    {
        this.syncStatus = -1;
    }

    public Boiler(JSONObject jsonObject)
    {
        try {
            this.bName = jsonObject.getString("boilerName");
            this.bCapacity = jsonObject.getString("boilerCapacity");
            this.bLocation = jsonObject.getString("boilerLocation");
            this.bMake = jsonObject.getString("boilerMake");
            this.bTemp = jsonObject.getString("boilerTemperature");
            this.bPressure = jsonObject.getString("boilerPressure");
            this.bHealthStatus = jsonObject.getString("boilerHealthStatus");
            this.bCurrentContainment = jsonObject.getString("boilerCurrentContainment");
            this.boilerID = jsonObject.getString("objectId");
            this.lastServerSyncDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
            this.lastUpdatedDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
        }

        catch (Exception ex)
        {
            Log.e("Boiler Model", "Failed to parse JSON due to: " + ex);
        }
    }

    protected Boiler(Parcel in) {
        client_id = in.readInt();
        long tmpLastServerSyncDate = in.readLong();
        lastServerSyncDate = tmpLastServerSyncDate != -1 ? new Date(tmpLastServerSyncDate) : null;
        long tmpLastUpdatedDate = in.readLong();
        lastUpdatedDate = tmpLastUpdatedDate != -1 ? new Date(tmpLastUpdatedDate) : null;
        syncStatus = in.readInt();
        bName = in.readString();
        bCapacity = in.readString();
        bLocation = in.readString();
        bMake = in.readString();
        bTemp = in.readString();
        bPressure = in.readString();
        bHealthStatus = in.readString();
        bCurrentContainment = in.readString();
        boilerID = in.readString();
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
        dest.writeString(bName);
        dest.writeString(bCapacity);
        dest.writeString(bLocation);
        dest.writeString(bMake);
        dest.writeString(bTemp);
        dest.writeString(bPressure);
        dest.writeString(bHealthStatus);
        dest.writeString(bCurrentContainment);
        dest.writeString(boilerID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Boiler> CREATOR = new Parcelable.Creator<Boiler>() {
        @Override
        public Boiler createFromParcel(Parcel in) {
            return new Boiler(in);
        }

        @Override
        public Boiler[] newArray(int size) {
            return new Boiler[size];
        }
    };
}
