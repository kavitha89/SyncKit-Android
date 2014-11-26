package com.example.android.network.sync.basicsyncadapter.models;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
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
            this.lastServerSyncDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
            this.lastUpdatedDate = dateFormatter.parse(jsonObject.getString("updatedAt"));
        }

        catch (Exception ex)
        {
            Log.e("Transformers Model", "Failed to parse JSON due to: " + ex);
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

            return newServerObjectJSON.toString();
        }

        catch (Exception ex)
        {

        }
        return "";
    }
}
