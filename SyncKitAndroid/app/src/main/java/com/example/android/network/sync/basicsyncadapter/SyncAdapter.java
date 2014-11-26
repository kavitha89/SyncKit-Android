/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.network.sync.basicsyncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.example.android.network.sync.basicsyncadapter.models.Boiler;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import org.json.*;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define a sync adapter for the app.
 *
 * <p>This class is instantiated in {@link SyncService}, which also binds SyncAdapter to the system.
 * SyncAdapter should only be initialized in SyncService, never anywhere else.
 *
 * <p>The system calls onPerformSync() via an RPC call through the IBinder object supplied by
 * SyncService.
 */
class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String TAG = "SyncAdapter";

    public ArrayList<Class> modelsRegisteredForSync;

    /**
     * URL to fetch content from during a sync.
     *
     * <p>This points to the Android Developers Blog. (Side note: We highly recommend reading the
     * Android Developer Blog to stay up to date on the latest Android platform developments!)
     */
    private static final String FEED_URL = "https://api.parse.com/1/classes/Transformer";

    /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds

    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds

    /**
     * Content resolver, for performing database operations.
     */
    private final ContentResolver mContentResolver;


    // Constants representing column positions from PROJECTION.
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_ENTRY_ID = 1;
    public static final int COLUMN_TITLE = 2;
    public static final int COLUMN_LINK = 3;
    public static final int COLUMN_PUBLISHED = 4;

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        addModelsToSync();
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        addModelsToSync();
    }


    private void addModelsToSync()
    {
        modelsRegisteredForSync = new ArrayList<Class>();
        modelsRegisteredForSync.add(Transformer.class);

    }
    /**
     * Called by the Android system in response to a request to run the sync adapter. The work
     * required to read data from the network, parse it, and store it in the content provider is
     * done here. Extending AbstractThreadedSyncAdapter ensures that all methods within SyncAdapter
     * run on a background thread. For this reason, blocking I/O and other long-running tasks can be
     * run <em>in situ</em>, and you don't have to set up a separate thread for them.
     .
     *
     * <p>This is where we actually perform any work required to perform a sync.
     * {@link AbstractThreadedSyncAdapter} guarantees that this will be called on a non-UI thread,
     * so it is safe to peform blocking I/O here.
     *
     * <p>The syncResult argument allows you to pass information back to the method that triggered
     * the sync.
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "Beginning network synchronization");
        try {
            final URL location = new URL(FEED_URL);
            InputStream stream = null;

            try {
                Log.i(TAG, "Streaming data from network: " + location);
                downloadAllObjectsForRegisteredModels(syncResult);
                //stream = downloadUrl(location);
                //updateLocalFeedData(stream, syncResult);
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            }
            finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        catch (InvocationTargetException e)
        {

        }

        catch (IllegalAccessException e)
        {

        }

        catch (MalformedURLException e) {
            Log.wtf(TAG, "Feed URL is malformed", e);
            syncResult.stats.numParseExceptions++;
            return;
        } catch (IOException e) {
            Log.e(TAG, "Error reading from network: " + e.toString());
            syncResult.stats.numIoExceptions++;
            return;
        } /*catch (XmlPullParserException e) {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
            return;
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
            return;
        } catch (RemoteException e) {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        }*/
        Log.i(TAG, "Network synchronization complete");
    }

    /**
     * Read XML from an input stream, storing it into the content provider.
     *
     * <p>This is where incoming data is persisted, committing the results of a sync. In order to
     * minimize (expensive) disk operations, we compare incoming data with what's already in our
     * database, and compute a merge. Only changes (insert/update/delete) will result in a database
     * write.
     *
     * <p>As an additional optimization, we use a batch operation to perform all database writes at
     * once.
     *
     * <p>Merge strategy:
     * 1. Get cursor to all items in feed<br/>
     * 2. For each item, check if it's in the incoming data.<br/>
     *    a. YES: Remove from "incoming" list. Check if data has mutated, if so, perform
     *            database UPDATE.<br/>
     *    b. NO: Schedule DELETE from database.<br/>
     * (At this point, incoming database only contains missing items.)<br/>
     * 3. For any items remaining in incoming list, ADD to database.
     */
    public void updateLocalFeedData(final InputStream stream, final SyncResult syncResult)
            throws IOException, XmlPullParserException, RemoteException,
            OperationApplicationException, ParseException {


        final ContentResolver contentResolver = getContext().getContentResolver();

        Log.i(TAG, "Parsing stream as Atom feed");
        final List<Transformer> entries = null; /*=this.parseTransformersResponse(stream)*/;
        ;
        Log.i(TAG, "Parsing complete. Found " + entries.size() + " entries");


        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();

        // Build hash table of incoming entries
        HashMap<String, Transformer> entryMap = new HashMap<String, Transformer>();
        for (Transformer e : entries) {
            entryMap.put(e.transformerID, e);
        }
        Cursor c = null;

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

            id = c.getColumnName(COLUMN_ID);
            name = c.getString(COLUMN_ENTRY_ID);
            location = c.getString(COLUMN_TITLE);

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
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        mContentResolver.applyBatch(Transformer.CONTENT_AUTHORITY, batch);
        mContentResolver.notifyChange(
                Transformer.CONTENT_URI, // URI where data was modified
                null,                           // No local observer
                false);                         // IMPORTANT: Do not sync to network
        // This sample doesn't support uploads, but if *your* code does, make sure you set
        // syncToNetwork=false in the line above to prevent duplicate syncs.
    }



    private void downloadAllObjectsForRegisteredModels(SyncResult syncResults) throws InvocationTargetException, IllegalAccessException {
        //first see if there are any rows with sync status with 0, else fetch all

        SyncResult result = syncResults;
        for (Class sClass : modelsRegisteredForSync)
        {
            try {
                result = doFetchAllForURL(sClass,syncResults);
            }

            catch (Exception ex)
            {

            }

        }

        postAllDirtyRecordsToServer(result);
    }

    private void downloadDeltaForAllRegisteredModels()
    {
        for (Class sClass : modelsRegisteredForSync)
        {
            try {
                //doFetchAllForURL(sClass);
            }

            catch (Exception ex)
            {

            }

        }
    }

    private void postAllDirtyRecordsToServer(SyncResult syncResult)
    {
        SyncResult result = syncResult;

        for (Class sClass : modelsRegisteredForSync)
        {
            try {

                Class[] cArg = new Class[1];
                cArg[0] = Context.class;

                Method methodForGettingDirtyObjects = sClass.getDeclaredMethod("fetchAllDirtyObjectsInDB",cArg);

                ArrayList<Object> objectsToBeUpdated = (ArrayList<Object>) methodForGettingDirtyObjects.invoke(null,getContext());

                for (Object obj:objectsToBeUpdated)
                {
                    updateObjectAtServerForObject(obj,syncResult);
                }

            }

            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }

        }

        postAllNewlyCreatedRecordsToServer(result);

    }

    private void postAllNewlyCreatedRecordsToServer(SyncResult syncResult)
    {
        SyncResult result = syncResult;

        for (Class sClass : modelsRegisteredForSync)
        {
            try {

                Class[] cArg = new Class[1];
                cArg[0] = Context.class;

                Method methodForNewlyCreatedObjects = sClass.getDeclaredMethod("fetchAllNewObjectsInDB",cArg);

                ArrayList<Object> objectsToBeCreated = (ArrayList<Object>) methodForNewlyCreatedObjects.invoke(null,getContext());

                for (Object obj:objectsToBeCreated)
                {
                    createNewObjectAtServerForObject(obj,syncResult);
                }

            }

            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }

        }

        deleteAllDeletedRecordsAtServer(result);
    }

    private void deleteAllDeletedRecordsAtServer(SyncResult syncResult)
    {
        for (Class sClass : modelsRegisteredForSync)
        {
            try {

                Class[] cArg = new Class[1];
                cArg[0] = Context.class;

                Method methodToGetDeletedObjects = sClass.getDeclaredMethod("fetchAllDeletedObjectsInDB",cArg);

                ArrayList<Object> objectsToBeDeleted = (ArrayList<Object>) methodToGetDeletedObjects.invoke(null,getContext());

                for (Object obj:objectsToBeDeleted)
                {
                    deleteObjectAtServerForObject(obj,syncResult);
                }

            }

            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }

        }

        syncCompleted();
    }

    private void syncCompleted()
    {

    }
    /**
     * Given a string representation of a URL, sets up a connection and gets an input stream.
     */
    private SyncResult doFetchAllForURL(Class sClass,SyncResult sResults) throws IOException {

        try {
        Method urlForFetchAllMethod = sClass.getDeclaredMethod("urlForFetchAll",null);
        String urlForFetchAll = (String)urlForFetchAllMethod.invoke(null,null);

        System.out.println(urlForFetchAll);
        final URL fetchAllURL = new URL(urlForFetchAll);

            HttpURLConnection conn = (HttpURLConnection) fetchAllURL.openConnection();
            conn.setRequestProperty("X-Parse-Application-Id","TsEDR12ICJtD59JM92WslVurN0wh5JPuznKvroRc");
            conn.setRequestProperty("X-Parse-REST-API-Key","4LC6oFNCyqLMFHSdPIPsxJoXHY6gTHGMG2kUcbwB");
            conn.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
            conn.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            Log.i(TAG, "Response from parse.com : " + conn.getResponseMessage());
            Log.i(TAG, "Status Code from parse.com : " + conn.getResponseCode());

            Class[] cArg = new Class[3];
            cArg[0] = Context.class;
            cArg[1] = InputStream.class;
            cArg[2] = SyncResult.class;

            Method handleDataForModel = sClass.getDeclaredMethod("handleInsertWithData", cArg);
            SyncResult objectsUpdated = (SyncResult) handleDataForModel.invoke(null, this.getContext(),conn.getInputStream(),sResults);
            return objectsUpdated;
        }

        catch (Exception ex)
        {
            Log.i(TAG,"exception " + ex.toString());
        }

        return sResults;
    }

    private SyncResult createNewObjectAtServerForObject(Object obj,SyncResult sResults)
    {
        try {
            System.out.println(obj.getClass());
            Method methodForCreateNewServerObjectURL = obj.getClass().getDeclaredMethod("urlForNewServerObject", null);
            String urlForNewServerObject = (String)methodForCreateNewServerObjectURL.invoke(obj,null);

            Method methodForPostBodyForUpdateServerObject = obj.getClass().getDeclaredMethod("postBodyForNewServerObject", null);
            String postBodyForUpdateServerObject = (String)methodForPostBodyForUpdateServerObject.invoke(obj,null);

            System.out.println(urlForNewServerObject);

            InputStream inputStream = null;
            String result = "";

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlForNewServerObject);
            StringEntity se = new StringEntity(postBodyForUpdateServerObject);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("X-Parse-Application-Id", "TsEDR12ICJtD59JM92WslVurN0wh5JPuznKvroRc");
            httpPost.setHeader("X-Parse-REST-API-Key", "4LC6oFNCyqLMFHSdPIPsxJoXHY6gTHGMG2kUcbwB");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                System.out.println(result);
                if(httpResponse.getStatusLine().getStatusCode() == 201)
                {
                    Class[] cArg = new Class[2];
                    cArg[0] = String.class;
                    cArg[1] = Context.class;

                    Method handleInsertResponseForObjectMethod = obj.getClass().getDeclaredMethod("handleObjectCreateResponseFromServer", cArg);
                    String setSynced = (String)handleInsertResponseForObjectMethod.invoke(obj,result,getContext());
                    if(setSynced.equals("1"))
                        System.out.println("Success!!");
                }
            }
            else
                result = "Did not work!";

        }
        catch (Exception ex)
        {
            Log.i(TAG,"exception " + ex.toString());
        }

        return sResults;
    }

    private SyncResult updateObjectAtServerForObject(Object obj,SyncResult sResults)
    {
        try {
            System.out.println(obj.getClass());
            Method methodForUpdateServerObjectURL = obj.getClass().getDeclaredMethod("urlForUpdateServerObject", null);
            String urlForUpdateServerObject = (String)methodForUpdateServerObjectURL.invoke(obj,null);

            Method methodForPostBodyForUpdateServerObject = obj.getClass().getDeclaredMethod("postBodyForUpdateServerObject", null);
            String postBodyForUpdateServerObject = (String)methodForPostBodyForUpdateServerObject.invoke(obj,null);

            System.out.println(urlForUpdateServerObject);

            InputStream inputStream = null;
            String result = "";

                HttpClient httpclient = new DefaultHttpClient();
                HttpPut httpPost = new HttpPut(urlForUpdateServerObject);
                StringEntity se = new StringEntity(postBodyForUpdateServerObject);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("X-Parse-Application-Id", "TsEDR12ICJtD59JM92WslVurN0wh5JPuznKvroRc");
                httpPost.setHeader("X-Parse-REST-API-Key", "4LC6oFNCyqLMFHSdPIPsxJoXHY6gTHGMG2kUcbwB");
                HttpResponse httpResponse = httpclient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    System.out.println(result);
                    if(httpResponse.getStatusLine().getStatusCode() == 200)
                    {

                        Class[] cArg = new Class[2];
                        cArg[0] = String.class;
                        cArg[1] = Context.class;

                        Method handleObjectUpdateResponseFromServerUpdateResponseForObjectMethod = obj.getClass().getDeclaredMethod("handleObjectUpdateResponseFromServer", cArg);
                        String setSynced = (String)handleObjectUpdateResponseFromServerUpdateResponseForObjectMethod.invoke(obj,result,getContext());
                        if(setSynced.equals("1"))
                            System.out.println("Success!!");
                    }
                }
                else
                    result = "Did not work!";

            }
        catch (Exception ex)
        {
            Log.i(TAG,"exception " + ex.toString());
        }

        return sResults;
    }

    private SyncResult deleteObjectAtServerForObject(Object obj,SyncResult sResults)
    {
        try {
            Method methodForDeleteServerObject = obj.getClass().getDeclaredMethod("urlForDeleteServerObject", null);
            String urlForDeleteServerObject = (String)methodForDeleteServerObject.invoke(obj,null);


            System.out.println(urlForDeleteServerObject);
            final URL fetchAllURL = new URL(urlForDeleteServerObject);

            InputStream inputStream = null;
            String result = "";

            HttpClient httpclient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(urlForDeleteServerObject);
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Content-type", "application/json");
            httpDelete.setHeader("X-Parse-Application-Id", "TsEDR12ICJtD59JM92WslVurN0wh5JPuznKvroRc");
            httpDelete.setHeader("X-Parse-REST-API-Key", "4LC6oFNCyqLMFHSdPIPsxJoXHY6gTHGMG2kUcbwB");
            HttpResponse httpResponse = httpclient.execute(httpDelete);
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                System.out.println(result);
                if(httpResponse.getStatusLine().getStatusCode() == 200)
                {
                    Class[] cArg = new Class[2];
                    cArg[0] = String.class;
                    cArg[1] = Context.class;

                    Method handleObjectDeleteResponseFromServer = obj.getClass().getDeclaredMethod("handleObjectDeleteResponseFromServer", cArg);
                    String setSynced = (String)handleObjectDeleteResponseFromServer.invoke(obj,result,getContext());
                    if(setSynced.equals("1"))
                        System.out.println("Success!!");
                }
            }
        }

        catch (Exception ex)
        {
            Log.i(TAG,"exception " + ex.toString());
        }

        return sResults;
    }

    private void updateDataBaseForModel(Class syncModel,SyncResult syncResults,InputStream stream)
    {
        //1. parse data and get array of objects.
        //2. determine what kind of class it is
        //3. start iterating the parsed data and check for previous objects with same id.
        //4. if it does'nt exist then directly insert.
        //5. if it exists then check its syncFlag.
        //6. if syncFlag is 0 then force update.
        //7. if syncFlag is anything other than 0 then mark that record as conflicted.
        //8. go to next record.

        try {

        }
        catch (Exception ex)
        {

        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
