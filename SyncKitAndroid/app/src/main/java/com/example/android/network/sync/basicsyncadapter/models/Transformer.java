package com.example.android.network.sync.basicsyncadapter.models;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import com.example.android.network.sync.basicsyncadapter.provider.FeedProvider;
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

    public String identificationAttributeValue()
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

    public String handleObjectUpdateResponseFromServer(String response,ContentResolver resolver)
    {
        try {
            JSONObject updateResponse = new JSONObject(response);
            if(updateResponse.has("updatedAt"))
            {
                //update row in db - set id and lastUpdatedDate
                this.lastServerSyncDate = dateFormatter.parse(updateResponse.getString("updatedAt"));
                this.lastUpdatedDate = dateFormatter.parse(updateResponse.getString("updatedAt"));
                this.syncStatus = Constants.SYNC_STATUS.SYNCED.getValue();
                if(this.commitObjectToDB(resolver))
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

    public String handleObjectCreateResponseFromServer(String response,ContentResolver resolver)
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
                if(this.commitObjectToDB(resolver))
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

    public static ArrayList<Transformer>fetchAllAvailableObjectsInDB(ContentResolver resolver)
    {

        try {

            final ContentResolver contentResolver = resolver;

            Uri uri = Transformer.CONTENT_URI; // Get all entries

            String mSelectionClause = Transformer.KEY_SYNC_STATUS + " != ?";

            String[] mSelectionArgs = {""};

            mSelectionArgs[0] = Integer.toString(Constants.SYNC_STATUS.DELETED.getValue());

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

    public static ArrayList<Transformer>fetchAllDirtyObjectsInDB(ContentResolver resolver)
    {
        return fetchAllTransformerObjectsInDBWithSyncStatus(resolver, Constants.SYNC_STATUS.DIRTY.getValue());
    }

    public static ArrayList<Transformer>fetchAllNewObjectsInDB(ContentResolver resolver)
    {
        return fetchAllTransformerObjectsInDBWithSyncStatus(resolver,Constants.SYNC_STATUS.INSERTED.getValue());
    }

    public static ArrayList<Transformer>fetchAllDeletedObjectsInDB(ContentResolver resolver)
    {

        return fetchAllTransformerObjectsInDBWithSyncStatus(resolver, Constants.SYNC_STATUS.DELETED.getValue());
    }

    public static String setObjectAsSynced(String objectID, ContentResolver resolver)
    {
        if(setSyncFlagForTransformerObjectWithId(objectID, Constants.SYNC_STATUS.SYNCED.getValue(),resolver))
            return "1";
        else
            return "0";
    }

    public static String setObjectAsDirty(String objectID, ContentResolver resolver)
    {
        if(setSyncFlagForTransformerObjectWithId(objectID, Constants.SYNC_STATUS.DIRTY.getValue(),resolver))
            return "1";
        else
            return "0";
    }

    public static String setObjectAsConflicted(String objectID, ContentResolver resolver)
    {
        if(setSyncFlagForTransformerObjectWithId(objectID, Constants.SYNC_STATUS.CONFLICTED.getValue(),resolver))
            return "1";
        else
            return "0";
    }

    public static String setObjectAsNew(String objectID, ContentResolver resolver)
    {
        if(setSyncFlagForTransformerObjectWithId(objectID, Constants.SYNC_STATUS.INSERTED.getValue(),resolver))
            return "1";
        else
            return "0";
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


                /* TODO read as dates. */

                System.out.println(c.getColumnCount());
                if (!c.isNull(10)) {

                    System.out.println(c.getType(10));
                    Date date = dateFormatter.parse(c.getString(10));
                    transformer.lastUpdatedDate = date;

                }

                if (!c.isNull(11)) {

                    System.out.println(c.getString(11));
                    Date date = dateFormatter.parse(c.getString(11));
                    transformer.lastServerSyncDate = date;

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

    public boolean saveTransformerObjectToDB(ContentResolver resolver)
    {
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        Date currentDate = new Date();

        batch.add(ContentProviderOperation.newInsert(Transformer.CONTENT_URI)
                .withValue(Transformer.KEY_NAME, this.trsName)
                .withValue(Transformer.KEY_LOCATION, this.trsLocation)
                .withValue(Transformer.KEY_CURRENT_TEMP, this.trsCurrentTemp)
                .withValue(Transformer.KEY_LAST_UPDATED_TIME, dateFormatter.format(currentDate))
                .withValue(Transformer.KEY_SYNC_STATUS, Constants.SYNC_STATUS.INSERTED.getValue())
                .withValue(Transformer.KEY_MAKE, this.trsMake)
                .withValue(Transformer.KEY_WINDING_MAKE, this.trsWindingMake)
                .withValue(Transformer.KEY_WINDING_COUNT, this.trsWindingCount)
                .withValue(Transformer.KEY_OIL_LEVEL, this.trsOilLevel)
                .withValue(Transformer.KEY_OPERATING_POWER, this.trsOperatingPower)
                .withValue(Transformer.KEY_TYPE, this.trsType)
                .build());
        try {

            resolver.applyBatch(Transformer.CONTENT_AUTHORITY, batch);
            resolver.notifyChange(
                    Transformer.CONTENT_URI, // URI where data was modified
                    null,                           // No local observer
                    false);
            return true;
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        return false;
    }

    public boolean commitObjectToDB(ContentResolver resolver)
    {
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        Date currentDate = new Date();

        Uri existingUri = Transformer.CONTENT_URI.buildUpon()
                .appendPath(this.transformerID).build();

        batch.add(ContentProviderOperation.newUpdate(existingUri)
                .withValue(Transformer.KEY_NAME, this.trsName)
                .withValue(Transformer.KEY_LOCATION, this.trsLocation)
                .withValue(Transformer.KEY_CURRENT_TEMP, this.trsCurrentTemp)
                .withValue(Transformer.KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(this.lastServerSyncDate))
                .withValue(Transformer.KEY_LAST_UPDATED_TIME, dateFormatter.format(currentDate))
                .withValue(Transformer.KEY_SYNC_STATUS, this.syncStatus)
                .withValue(Transformer.KEY_MAKE, this.trsMake)
                .withValue(Transformer.KEY_WINDING_MAKE, this.trsWindingMake)
                .withValue(Transformer.KEY_WINDING_COUNT, this.trsWindingCount)
                .withValue(Transformer.KEY_OIL_LEVEL, this.trsOilLevel)
                .withValue(Transformer.KEY_OPERATING_POWER, this.trsOperatingPower)
                .withValue(Transformer.KEY_TYPE, this.trsType)
                .build());
        try {

            resolver.applyBatch(Transformer.CONTENT_AUTHORITY, batch);
            resolver.notifyChange(
                    Transformer.CONTENT_URI, // URI where data was modified
                    null,                           // No local observer
                    false);
            return true;
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        return false;
    }

    public boolean updateTransformerObjectInDB(ContentResolver resolver)
    {
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        int sync_flag = this.syncStatus==Constants.SYNC_STATUS.INSERTED.getValue()?Constants.SYNC_STATUS.INSERTED.getValue():Constants.SYNC_STATUS.DIRTY.getValue();

        Uri existingUri = Transformer.CONTENT_URI.buildUpon()
                .appendPath(this.transformerID).build();
        Log.i(TAG, "Scheduling update: " + existingUri);

        Date currentDate = new Date();

        batch.add(ContentProviderOperation.newUpdate(existingUri)
                .withValue(Transformer.KEY_NAME, this.trsName)
                .withValue(Transformer.KEY_LOCATION, this.trsLocation)
                .withValue(Transformer.KEY_CURRENT_TEMP, this.trsCurrentTemp)
                .withValue(Transformer.KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(this.lastServerSyncDate))
                .withValue(Transformer.KEY_LAST_UPDATED_TIME, dateFormatter.format(currentDate))
                .withValue(Transformer.KEY_SYNC_STATUS, sync_flag)
                .withValue(Transformer.KEY_MAKE, this.trsMake)
                .withValue(Transformer.KEY_WINDING_MAKE, this.trsWindingMake)
                .withValue(Transformer.KEY_WINDING_COUNT, this.trsWindingCount)
                .withValue(Transformer.KEY_OIL_LEVEL, this.trsOilLevel)
                .withValue(Transformer.KEY_OPERATING_POWER, this.trsOperatingPower)
                .withValue(Transformer.KEY_TYPE, this.trsType)
                .build());
        try {

            resolver.applyBatch(Transformer.CONTENT_AUTHORITY, batch);
            resolver.notifyChange(
                    Transformer.CONTENT_URI, // URI where data was modified
                    null,                           // No local observer
                    false);
            return true;
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        return false;
    }

    public static boolean setSyncFlagForTransformerObjectWithId(String transformerID,int syncStatus, ContentResolver resolver)
    {
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        Uri existingUri = Transformer.CONTENT_URI.buildUpon()
                .appendPath(transformerID).build();
        Log.i(TAG, "Scheduling update: " + existingUri);
        batch.add(ContentProviderOperation.newUpdate(existingUri)
                .withValue(Transformer.KEY_SYNC_STATUS, syncStatus)
                .build());
        try {

            resolver.applyBatch(Transformer.CONTENT_AUTHORITY, batch);
            resolver.notifyChange(
                    Transformer.CONTENT_URI, // URI where data was modified
                    null,                           // No local observer
                    false);
            return true;
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        return false;
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

            FeedProvider feedProvider = new FeedProvider();
            feedProvider.mDatabaseHelper.insertObject(e);

            /*if(isTransformerObjectWithIDExistsInArray(transformerListFromDB, e.transformerID))
            {
                //update the objects with sync_status = 0, else leave for now
                System.out.println("Yes Object Exists");

                if(getSyncStatusForObjectWithID(transformerListFromDB, e.transformerID) == Constants.SYNC_STATUS.SYNCED.getValue())
                {
                    Uri existingUri = Transformer.CONTENT_URI.buildUpon()
                            .appendPath(e.transformerID).build();
                        // Update existing record
                        Log.i(TAG, "Scheduling update: " + existingUri);
                        batch.add(ContentProviderOperation.newUpdate(existingUri)
                                .withValue(Transformer.KEY_NAME, e.trsName)
                                .withValue(Transformer.KEY_LOCATION, e.trsLocation)
                                .withValue(Transformer.KEY_CURRENT_TEMP, e.trsCurrentTemp)
                                .withValue(Transformer.KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(e.lastServerSyncDate))
                                .withValue(Transformer.KEY_LAST_UPDATED_TIME, dateFormatter.format(e.lastServerSyncDate))
                                .withValue(Transformer.KEY_SYNC_STATUS, Constants.SYNC_STATUS.SYNCED.getValue())
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
                        .withValue(Transformer.KEY_LAST_SERVER_SYNC_DATE, dateFormatter.format(e.lastServerSyncDate))
                        .withValue(Transformer.KEY_LAST_UPDATED_TIME, dateFormatter.format(e.lastServerSyncDate))
                        .withValue(Transformer.KEY_SYNC_STATUS, Constants.SYNC_STATUS.SYNCED.getValue())
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

        Log.i(TAG, "Merge solution ready. Applying batch update");

        try {
            contentResolver.applyBatch(Transformer.CONTENT_AUTHORITY, batch);
            contentResolver.notifyChange(
                    Transformer.CONTENT_URI, // URI where data was modified
                    null,                           // No local observer
                    false);
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }*/
        }
                       // IMPORTANT: Do not sync to network
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
