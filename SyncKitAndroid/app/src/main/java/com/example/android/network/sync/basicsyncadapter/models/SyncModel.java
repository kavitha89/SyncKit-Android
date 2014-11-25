package com.example.android.network.sync.basicsyncadapter.models;

/**
 * Created by Veni on 11/25/14.
 */
public class SyncModel {

    public static String KEY_CLIENT_ID = "client_id";

    public String TABLE_NAME = "";

    public int client_id;

    public static String columnNameForIdentificationAttribute()
    {
        return KEY_CLIENT_ID;
    }

}
