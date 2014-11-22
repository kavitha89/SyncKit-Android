package com.example.android.network.sync.basicsyncadapter.models;

import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Veni on 11/10/14.
 */
public class Transformer extends HashMap<String,String> {

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.network.sync.basicsyncadapter";

    /**
     * Base URI. (content://com.example.android.network.sync.basicsyncadapter)
     */

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ENTRIES = "results";

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

    public static final String[] PROJECTION = new String[]{
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
    public static String TABLE_NAME = "Transformers";
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

    @Override
    public String get(Object k) {
        String key = (String) k;
        if (KEY_NAME.equals(key))
            return trsName;
        else if (KEY_LOCATION.equals(key))
            return trsLocation;
        return null;
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
            //this.lastServerSyncDate = jsonObject.getString("lastUpdatedAt");
        }

        catch (Exception ex)
        {
            Log.e("Transformers Model", "Failed to parse JSON due to: " + ex);
        }
    }

    @SerializedName("lastUpdatedAt")
    public Date lastServerSyncDate;

    //additional values for sync
    public Date lastUpdatedDate;
    public long syncStatus;

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

}
