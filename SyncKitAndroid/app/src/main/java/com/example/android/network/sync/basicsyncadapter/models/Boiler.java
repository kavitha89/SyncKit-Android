package com.example.android.network.sync.basicsyncadapter.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Veni on 11/10/14.
 */
public class Boiler extends SyncModel {

    public static String KEY_NAME = "trsName";
    public static String KEY_LOCATION = "trsLocation";

    public Boiler(String name, String location) {
        this.trsName = name;
        this.trsLocation = location;
    }

    @SerializedName("lastUpdatedAt")
    public Date lastServerSyncDate;
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
