package com.example.android.network.sync.basicsyncadapter.models;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Veni on 11/10/14.
 */
public class Pipe extends SyncModel implements Parcelable {

    public Date lastServerSyncDate;
    public Date lastUpdatedDate;
    public int syncStatus;

    public String pDiameter;
    public String pLocation;
    public String pLength;
    public String pMake;
    public String pHealthStatus;
    public String pCurrentContainment;
    public String pPressure;
    public String pTemperature;
    public String pMaxPressure;
    public String pMaxTemperature;
    public String pipeID;

    protected Pipe(Parcel in) {
        TABLE_NAME = in.readString();
        client_id = in.readInt();
        long tmpLastServerSyncDate = in.readLong();
        lastServerSyncDate = tmpLastServerSyncDate != -1 ? new Date(tmpLastServerSyncDate) : null;
        long tmpLastUpdatedDate = in.readLong();
        lastUpdatedDate = tmpLastUpdatedDate != -1 ? new Date(tmpLastUpdatedDate) : null;
        syncStatus = in.readInt();
        pDiameter = in.readString();
        pLocation = in.readString();
        pLength = in.readString();
        pMake = in.readString();
        pHealthStatus = in.readString();
        pCurrentContainment = in.readString();
        pPressure = in.readString();
        pTemperature = in.readString();
        pMaxPressure = in.readString();
        pMaxTemperature = in.readString();
        pipeID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TABLE_NAME);
        dest.writeInt(client_id);
        dest.writeLong(lastServerSyncDate != null ? lastServerSyncDate.getTime() : -1L);
        dest.writeLong(lastUpdatedDate != null ? lastUpdatedDate.getTime() : -1L);
        dest.writeInt(syncStatus);
        dest.writeString(pDiameter);
        dest.writeString(pLocation);
        dest.writeString(pLength);
        dest.writeString(pMake);
        dest.writeString(pHealthStatus);
        dest.writeString(pCurrentContainment);
        dest.writeString(pPressure);
        dest.writeString(pTemperature);
        dest.writeString(pMaxPressure);
        dest.writeString(pMaxTemperature);
        dest.writeString(pipeID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pipe> CREATOR = new Parcelable.Creator<Pipe>() {
        @Override
        public Pipe createFromParcel(Parcel in) {
            return new Pipe(in);
        }

        @Override
        public Pipe[] newArray(int size) {
            return new Pipe[size];
        }
    };

    public static String TABLE_NAME = "pipes";
    public static String KEY_PIPE_LOCATION = "pipeLocation";
    public static String KEY_PIPE_MAKE = "pipeMake";
    public static String KEY_PIPE_TEMP = "pipeCurrentTemperature";
    public static String KEY_PIPE_PRESSURE = "pipeCurrentPressure";
    public static String KEY_PIPE_DIAMETER = "pipeDiameter";
    public static String KEY_PIPE_LENGTH = "pipeLength";
    public static String KEY_PIPE_CURRENT_CONTAINMENT = "pipeCurrentContainment";
    public static String KEY_PIPE_MAX_PRESSURE = "pipeMaxPressure";
    public static String KEY_PIPE_MAX_TEMPERATURE = "pipeMaxTemperature";
    public static String KEY_PIPE_HEALTH_STATUS = "pipeHealthStatus";
    public static String KEY_PIPE_ID = "objectId";
    public static String KEY_SYNC_STATUS = "SyncStatus";
    public static String KEY_LAST_UPDATED_TIME = "lastUpdatedTime";
    public static String KEY_LAST_SERVER_SYNC_DATE = "lastServerSyncTime";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String PIPE_SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ( client_id" +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_PIPE_ID + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_LOCATION + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_MAKE + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_TEMP + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_PRESSURE + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_DIAMETER + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_LENGTH + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_MAX_PRESSURE + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_MAX_TEMPERATURE + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_CURRENT_CONTAINMENT + TYPE_TEXT + COMMA_SEP +
                    KEY_PIPE_HEALTH_STATUS + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_UPDATED_TIME + TYPE_TEXT + COMMA_SEP +
                    KEY_LAST_SERVER_SYNC_DATE + TYPE_TEXT + COMMA_SEP +
                    KEY_SYNC_STATUS + TYPE_INTEGER + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Pipe.TABLE_NAME;

    private static final String FETCH_ALL_URL = "https://api.parse.com/1/classes/Pipeline";

    private static final String NEW_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Pipeline";

    private static final String UPDATE_SERVER_OBJECT_URL = "https://api.parse.com/1/classes/Pipeline";

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

    public static String createTableQuery(){return PIPE_SQL_CREATE_ENTRIES; }

    public static String deleteTableQuery(){return SQL_DELETE_ENTRIES; }

    public ContentValues contentValuesForInsert()
    {
        ContentValues values = new ContentValues();

        values.put(KEY_PIPE_LOCATION,this.pLocation);
        values.put(KEY_PIPE_MAKE, this.pMake);
        values.put(KEY_PIPE_TEMP, this.pTemperature);
        values.put(KEY_PIPE_PRESSURE, this.pPressure);
        values.put(KEY_PIPE_DIAMETER, this.pDiameter);
        values.put(KEY_PIPE_LENGTH, this.pLength);
        values.put(KEY_PIPE_CURRENT_CONTAINMENT, this.pCurrentContainment);
        values.put(KEY_PIPE_MAX_PRESSURE, this.pMaxPressure);
        values.put(KEY_PIPE_MAX_TEMPERATURE, this.pMaxTemperature);
        values.put(KEY_PIPE_HEALTH_STATUS,this.pHealthStatus);

        if(this.pipeID != null)
            values.put(KEY_PIPE_ID, this.pipeID);

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
        values.put(KEY_PIPE_LOCATION,this.pLocation);
        values.put(KEY_PIPE_MAKE, this.pMake);
        values.put(KEY_PIPE_TEMP, this.pTemperature);
        values.put(KEY_PIPE_PRESSURE, this.pPressure);
        values.put(KEY_PIPE_DIAMETER, this.pDiameter);
        values.put(KEY_PIPE_LENGTH, this.pLength);
        values.put(KEY_PIPE_CURRENT_CONTAINMENT, this.pCurrentContainment);
        values.put(KEY_PIPE_MAX_PRESSURE, this.pMaxPressure);
        values.put(KEY_PIPE_MAX_TEMPERATURE, this.pMaxTemperature);
        values.put(KEY_PIPE_HEALTH_STATUS,this.pHealthStatus);

        if(this.pipeID != null)
            values.put(KEY_PIPE_ID, this.pipeID);

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


    public String urlForUpdateServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.pipeID; }

    public String urlForDeleteServerObject(){return UPDATE_SERVER_OBJECT_URL + "/" + this.pipeID; }

    public String postBodyForNewServerObject()
    {
        try {

            /*
    public static String KEY_PIPE_LOCATION = "pipeLocation";
    public static String KEY_PIPE_MAKE = "pipeMake";
    public static String KEY_PIPE_TEMP = "pipeCurrentTemperature";
    public static String KEY_PIPE_PRESSURE = "pipeCurrentPressure";
    public static String KEY_PIPE_DIAMETER = "pipeDiameter";
    public static String KEY_PIPE_LENGTH = "pipeLength";
    public static String KEY_PIPE_CURRENT_CONTAINMENT = "pipeCurrentContainment";
    public static String KEY_PIPE_MAX_PRESSURE = "pipeMaxPressure";
    public static String KEY_PIPE_MAX_TEMPERATURE = "pipeMaxTemperature";
    public static String KEY_PIPE_ID = "objectId";
            *
            * */
            JSONObject newServerObjectJSON = new JSONObject();
            newServerObjectJSON.put("pipeLocation", this.pLocation);
            newServerObjectJSON.put("pipeMake", this.pMake);
            newServerObjectJSON.put("pipeCurrentTemperature", this.pCurrentContainment);
            newServerObjectJSON.put("pipeCurrentPressure", this.pPressure);
            newServerObjectJSON.put("pipeDiameter", this.pDiameter);
            newServerObjectJSON.put("pipeLength", this.pLength);
            newServerObjectJSON.put("pipeCurrentContainment", this.pCurrentContainment);
            newServerObjectJSON.put("pipeMaxPressure", this.pMaxPressure);
            newServerObjectJSON.put("pipeMaxTemperature", this.pMaxTemperature);
            newServerObjectJSON.put("pipeHealthStatus", this.pHealthStatus);

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
            newServerObjectJSON.put("pipeLocation", this.pLocation);
            newServerObjectJSON.put("pipeMake", this.pMake);
            newServerObjectJSON.put("pipeCurrentTemperature", this.pCurrentContainment);
            newServerObjectJSON.put("pipeCurrentPressure", this.pPressure);
            newServerObjectJSON.put("pipeDiameter", this.pDiameter);
            newServerObjectJSON.put("pipeLength", this.pLength);
            newServerObjectJSON.put("pipeCurrentContainment", this.pCurrentContainment);
            newServerObjectJSON.put("pipeMaxPressure", this.pMaxPressure);
            newServerObjectJSON.put("pipeMaxTemperature", this.pMaxTemperature);
            newServerObjectJSON.put("pipeHealthStatus", this.pHealthStatus);

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
                this.pipeID = updateResponse.getString("objectId");
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

    public static ArrayList<Pipe> fetchAllAvailableObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllObjectsToDisplay(Pipe.class));
    }

    public static ArrayList<Pipe>fetchAllDirtyObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllDirtyObjectsForClass(Pipe.class));
    }

    public static ArrayList<Pipe>fetchAllNewObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllInsertedObjectsForClass(Pipe.class));
    }

    public static ArrayList<Pipe>fetchAllDeletedObjectsInDB(Context context)
    {
        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(context);
        return  generateArrayFromCursor(dbHelper.selectAllDeletedObjectsForClass(Pipe.class));
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

    private static ArrayList<Pipe> generateArrayFromCursor(Cursor c)
    {

        ArrayList<Pipe> pipes = new ArrayList<Pipe>();

        try {
            while (c.moveToNext()) {
                Pipe pipe = new Pipe();

                pipe.client_id = c.getInt(0);
                pipe.pipeID = c.getString(1);
                pipe.pLocation = c.getString(2);
                pipe.pMake = c.getString(3);
                pipe.pTemperature = c.getString(4);
                pipe.pPressure = c.getString(5);
                pipe.pDiameter = c.getString(6);
                pipe.pLength = c.getString(7);
                pipe.pMaxPressure = c.getString(8);
                pipe.pMaxTemperature = c.getString(9);
                pipe.pCurrentContainment = c.getString(10);
                pipe.pHealthStatus = c.getString(11);
                pipe.syncStatus = c.getInt(14);

                if (!c.isNull(12)) {
                    if(c.getString(12) != null) {
                        Date date = dateFormatter.parse(c.getString(12));
                        pipe.lastUpdatedDate = date;
                    }
                }

                if (!c.isNull(13)) {
                    if(c.getString(13) != null) {

                        Date date = dateFormatter.parse(c.getString(13));
                        pipe.lastServerSyncDate = date;
                    }
                }
                pipes.add(pipe);
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return pipes;
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

    public Pipe()
    {
        this.syncStatus = -1;
    }

    public Pipe(JSONObject jsonObject)
    {
        try {

            this.pLocation = jsonObject.getString("pipeLocation");
            this.pMake = jsonObject.getString("pipeMake");
            this.pTemperature = jsonObject.getString("pipeCurrentTemperature");
            this.pPressure = jsonObject.getString("pipeCurrentPressure");
            this.pDiameter = jsonObject.getString("pipeDiameter");
            this.pLength = jsonObject.getString("pipeLength");
            this.pCurrentContainment = jsonObject.getString("pipeCurrentContainment");
            this.pMaxPressure = jsonObject.getString("pipeMaxPressure");
            this.pMaxTemperature = jsonObject.getString("pipeMaxTemperature");
            this.pHealthStatus = jsonObject.getString("pipeHealthStatus");
            this.pipeID = jsonObject.getString("objectId");
            this.lastServerSyncDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
            this.lastUpdatedDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
        }

        catch (Exception ex)
        {
            Log.e("Pipe Model", "Failed to parse JSON due to: " + ex);
        }
    }


    private static boolean isPipeObjectWithIDExistsInArray(ArrayList<Pipe> pipes,String pipeID)
    {
        boolean isExists =false;

        for (Pipe e : pipes) {

            if(e.pipeID.equals(pipeID))
            {
                isExists = true;
                break;
            }
        }

        return isExists;
    }

    private static int getSyncStatusForObjectWithID(ArrayList<Pipe> pipes,String pipeID)
    {
        int sync_flag = -1;

        for (Pipe e : pipes) {

            if(e.pipeID.equals(pipeID))
            {
                sync_flag = e.syncStatus;
                break;
            }
        }

        return sync_flag;
    }

    private static int getClientIDForObjectWithID(ArrayList<Pipe> pipes,String pipeID)
    {
        int sync_flag = 0;

        for (Pipe e : pipes) {

            if(e.pipeID.equals(pipeID))
            {
                sync_flag = e.client_id;
                break;
            }
        }

        return sync_flag;
    }

    public static SyncResult handleInsertWithData(Context contentResolver,InputStream stream,SyncResult syncResult) throws RemoteException, OperationApplicationException {

        final List<Pipe> pipesListFromResponse = parsePipesResponse(stream);

        SyncDatabaseHelper dbHelper = SyncDatabaseHelper.getDataHelper(contentResolver);

        ArrayList<Pipe> pipeListFromDB = generateArrayFromCursor(dbHelper.selectAllObjectsOfClass(Pipe.class));


        for (Pipe e : pipesListFromResponse) {

            if (isPipeObjectWithIDExistsInArray(pipeListFromDB, e.pipeID)) {
                if (getSyncStatusForObjectWithID(pipeListFromDB, e.pipeID) == Constants.SYNC_STATUS.SYNCED.getValue()) {
                    e.client_id = getClientIDForObjectWithID(pipeListFromDB,e.pipeID);
                    dbHelper.udpateObject(e);
                }

            } else {
                dbHelper.insertObject(e);
            }
        }
        return syncResult;
    }

    private static ArrayList<Pipe> parsePipesResponse(InputStream stream)
    {
        ArrayList<Pipe> pipeArrayList = new ArrayList<Pipe>();

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
                Pipe pipeObject = new Pipe(ja.getJSONObject(i));
                pipeArrayList.add(pipeObject);
            }
        }
        catch (Exception ex) {
            Log.e("Pipe Model", "Failed to parse JSON due to: " + ex);
        }
        return pipeArrayList;
    }

}
