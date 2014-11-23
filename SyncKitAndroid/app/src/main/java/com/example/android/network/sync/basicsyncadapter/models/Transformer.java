package com.example.android.network.sync.basicsyncadapter.models;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

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
public class Transformer extends HashMap<String,String> {

    public static final String TAG = "Transformer Model";

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

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.basicsyncadapter.results";
    /**
     * MIME type for individual entries.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.basicsyncadapter.results";

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

    private static final String FETCH_ALL_URL = "https://api.parse.com/1/classes/Transformer";

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

    public static String columnNameForIdentificationAttribute()
    {
        return KEY_TRANSFORMER_ID;
    }


    public static ArrayList<Transformer>fetchAllTransformerObjectsInDB(ContentResolver resolver)
    {
        ArrayList<Transformer> transformers = new ArrayList<Transformer>();

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

            while (c.moveToNext()) {
                Transformer transformer = new Transformer();

                transformer.transformerID = c.getString(0);
                transformer.trsName = c.getString(1);
                transformer.trsLocation = c.getString(2);
                transformer.trsMake = c.getString(3);
                transformer.trsCurrentTemp = c.getString(4);
                transformer.trsOilLevel = c.getString(5);
                transformer.trsOperatingPower = c.getString(6);
                transformer.trsWindingCount = c.getString(7);
                transformer.trsWindingMake = c.getString(8);
                transformer.trsType = c.getString(9);
                transformer.syncStatus = c.getInt(12);

                //2014-11-10T13:17:21.210Z
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                /* TODO read as dates. */

                System.out.println(c.getColumnCount());
                if (!c.isNull(10)) {

                    System.out.println(c.getType(10));
                    System.out.println(c.getString(11));
                    Date date = formatter.parse(c.getString(10));
                    transformer.lastUpdatedDate = date;
                    date = formatter.parse(c.getString(11));
                    transformer.lastServerSyncDate = date;

                }



                transformers.add(transformer);
            }

            return transformers;
        }
        catch (Exception Ex)
        {
            Log.i(TAG,Ex.toString());
        }

        return null;
    }


    public static boolean isTransformerExistsWithIDInTable(String transformerID,ContentResolver resolver)
    {
        boolean isExists = false;

        try {

            final ContentResolver contentResolver = resolver;

            Uri uri = Transformer.CONTENT_URI; // Get all entries

            String[] mProjection =
            {
                Transformer.KEY_TRANSFORMER_ID
            };

            String mSelectionClause = Transformer.KEY_TRANSFORMER_ID + " = ?";

            String[] mSelectionArgs = {""};

            mSelectionArgs[0] = transformerID;

            Cursor c = contentResolver.query(uri, mProjection, mSelectionClause, mSelectionArgs, null);

            while (c.moveToNext()) {

                isExists = true;
                /* TO-DO read all properties. */
            }

        }
        catch (Exception Ex)
        {
            System.out.println(Ex.toString());
        }

        return isExists;
    }

    public static int getSyncStatusForObjectWithId(String transformerID,ContentResolver resolver)
    {

        int sync_status = -1;

        try {

            final ContentResolver contentResolver = resolver;

            Uri uri = Transformer.CONTENT_URI; // Get all entries

            String[] mProjection =
                    {
                            Transformer.KEY_SYNC_STATUS
                    };

            String mSelectionClause = Transformer.KEY_TRANSFORMER_ID + " = ?";

            String[] mSelectionArgs = {""};

            mSelectionArgs[0] = transformerID;

            Cursor c = contentResolver.query(uri, mProjection, mSelectionClause, mSelectionArgs, null);

            while (c.moveToNext()) {

                sync_status = c.getInt(0);
                return sync_status;
                /* TO-DO read all properties. */
            }

        }
        catch (Exception Ex)
        {
            System.out.println(Ex.toString());
        }

        return sync_status;
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

    public static SyncResult handleInsertWithData(ContentResolver contentResolver,InputStream stream,SyncResult syncResult) throws RemoteException, OperationApplicationException {

        final List<Transformer> transformersListFromResponse = parseTransformersResponse(stream);

        final ArrayList<Transformer> transformerListFromDB = fetchAllTransformerObjectsInDB(contentResolver);

        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        // Build hash table of incoming entries
        HashMap<String, Transformer> entryMap = new HashMap<String, Transformer>();

        for (Transformer e : transformersListFromResponse) {

            //entryMap.put(e.transformerID, e);

            if(isTransformerObjectWithIDExistsInArray(transformerListFromDB, e.transformerID))
            {
                //update the objects with sync_status = 0, else leave for now
                System.out.println("Yes Object Exists");

                if(getSyncStatusForObjectWithID(transformerListFromDB, e.transformerID) == 0)
                {
                    Uri existingUri = Transformer.CONTENT_URI.buildUpon()
                            .appendPath(e.transformerID).build();
                        // Update existing record
                        Log.i(TAG, "Scheduling update: " + existingUri);
                        batch.add(ContentProviderOperation.newUpdate(existingUri)
                                .withValue(Transformer.KEY_TRANSFORMER_ID, e.transformerID)
                                .withValue(Transformer.KEY_NAME, e.trsName)
                                .withValue(Transformer.KEY_LOCATION, e.trsLocation)
                                .withValue(Transformer.KEY_CURRENT_TEMP, e.trsCurrentTemp)
                                .withValue(Transformer.KEY_LAST_SERVER_SYNC_DATE, e.lastServerSyncDate)
                                .withValue(Transformer.KEY_LAST_UPDATED_TIME, e.lastServerSyncDate)
                                .withValue(Transformer.KEY_SYNC_STATUS, 0)
                                .withValue(Transformer.KEY_MAKE, e.trsMake)
                                .withValue(Transformer.KEY_WINDING_MAKE, e.trsWindingMake)
                                .withValue(Transformer.KEY_WINDING_COUNT, e.trsWindingCount)
                                .withValue(Transformer.KEY_OIL_LEVEL, e.trsOilLevel)
                                .withValue(Transformer.KEY_OPERATING_POWER, e.trsOperatingPower)
                                .withValue(Transformer.KEY_TYPE, e.trsType)
                                .build());
                        syncResult.stats.numUpdates++;
                }
            }

            else

            {
                //create the objects and set sync_status = 0

                batch.add(ContentProviderOperation.newInsert(Transformer.CONTENT_URI)
                        .withValue(Transformer.KEY_TRANSFORMER_ID, e.transformerID)
                        .withValue(Transformer.KEY_NAME, e.trsName)
                        .withValue(Transformer.KEY_LOCATION, e.trsLocation)
                        .withValue(Transformer.KEY_CURRENT_TEMP, e.trsCurrentTemp)
                        .withValue(Transformer.KEY_LAST_SERVER_SYNC_DATE, e.lastServerSyncDate)
                        .withValue(Transformer.KEY_LAST_UPDATED_TIME, e.lastServerSyncDate)
                        .withValue(Transformer.KEY_SYNC_STATUS, 0)
                        .withValue(Transformer.KEY_MAKE, e.trsMake)
                        .withValue(Transformer.KEY_WINDING_MAKE, e.trsWindingMake)
                        .withValue(Transformer.KEY_WINDING_COUNT, e.trsWindingCount)
                        .withValue(Transformer.KEY_OIL_LEVEL, e.trsOilLevel)
                        .withValue(Transformer.KEY_OPERATING_POWER, e.trsOperatingPower)
                        .withValue(Transformer.KEY_TYPE, e.trsType)
                        .build());
                syncResult.stats.numInserts++;
            }
        }

        /*Cursor c = null;

        try {

            // Get list of all items
            Log.i(TAG, "Fetching local entries for merge");
            Uri uri = Transformer.CONTENT_URI; // Get all entries
            c = contentResolver.query(uri, null, null, null, null);
            assert c != null;
            Log.i(TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");
        }

        catch (Exception ex)
        {

        }
        // Find stale data
        String id;
        String name;
        String location;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(0);
            name = c.getString(1);
            location = c.getString(2);

            Transformer match = entryMap.get(id);
            if (match != null) {
                // Entry exists. Remove from entry map to prevent insert later.
                entryMap.remove(id);
                // Check to see if the entry needs to be updated
                Uri existingUri = Transformer.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                if ((match.trsName != null && !match.trsLocation.equals(name))) {
                    // Update existing record
                    Log.i(TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(Transformer.KEY_NAME, name)
                            .withValue(Transformer.KEY_LOCATION, location)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No action: " + existingUri);
                }
            } else {
                // Entry doesn't exist. Remove it from the database.
                Uri deleteUri = Transformer.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Add new items
        for (Transformer e : entryMap.values()) {
            Log.i(TAG, "Scheduling insert: entry_id=" + e.transformerID);
            batch.add(ContentProviderOperation.newInsert(Transformer.CONTENT_URI)
                    .withValue(Transformer.KEY_TRANSFORMER_ID, e.transformerID)
                    .withValue(Transformer.KEY_NAME, e.trsName)
                    .withValue(Transformer.KEY_LOCATION, e.trsLocation)
                    .withValue(Transformer.KEY_CURRENT_TEMP, e.trsCurrentTemp)
                    .withValue(Transformer.KEY_LAST_SERVER_SYNC_DATE, e.lastServerSyncDate)
                    .withValue(Transformer.KEY_LAST_UPDATED_TIME, e.lastServerSyncDate)
                    .withValue(Transformer.KEY_SYNC_STATUS, 0)
                    .withValue(Transformer.KEY_MAKE, e.trsMake)
                    .withValue(Transformer.KEY_WINDING_MAKE, e.trsWindingMake)
                    .withValue(Transformer.KEY_WINDING_COUNT, e.trsWindingCount)
                    .withValue(Transformer.KEY_OIL_LEVEL, e.trsOilLevel)
                    .withValue(Transformer.KEY_OPERATING_POWER, e.trsOperatingPower)
                    .withValue(Transformer.KEY_TYPE, e.trsType)

                    .build());
            syncResult.stats.numInserts++;
        }*/
        Log.i(TAG, "Merge solution ready. Applying batch update");
        contentResolver.applyBatch(Transformer.CONTENT_AUTHORITY, batch);
        contentResolver.notifyChange(
                Transformer.CONTENT_URI, // URI where data was modified
                null,                           // No local observer
                false);                         // IMPORTANT: Do not sync to network
        // This sample doesn't support uploads, but if *your* code does, make sure you set
        // syncToNetwork=false in the line above to prevent duplicate syncs.
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

}
