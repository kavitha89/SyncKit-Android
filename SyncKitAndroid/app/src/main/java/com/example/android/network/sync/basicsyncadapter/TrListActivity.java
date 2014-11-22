package com.example.android.network.sync.basicsyncadapter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.network.sync.basicsyncadapter.adpaters.TransformerListAdapter;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TrListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_list);
        final ListView listview = (ListView) findViewById(R.id.trListview);

        List<Transformer> transformerList = buildData();
        String[] from = { Transformer.KEY_NAME,Transformer.KEY_LOCATION };
        int[] to = { android.R.id.text1,android.R.id.text2 };


        ListAdapter adapter = new TransformerListAdapter(this, transformerList, android.R.layout.simple_list_item_2, new String[] {
                Transformer.KEY_NAME, Transformer.KEY_LOCATION }, new int[] { android.R.id.text1, android.R.id.text2 });
        listview.setAdapter(adapter);
    }

    private List<Transformer> buildData() {

        try {

            List<Transformer> posts = new ArrayList<Transformer>();

            final ContentResolver contentResolver =this.getContentResolver();

            Cursor c = null;

            Uri uri = Transformer.CONTENT_URI; // Get all entries
            c = contentResolver.query(uri, null, null, null, null);

            while (c.moveToNext()) {

                Transformer transformer = new Transformer(c.getString(2),c.getString(3));
                posts.add(transformer);
            }

            /*JSONObject jso = new JSONObject(loadJSONFromAsset());
            JSONArray ja = jso.getJSONArray("results");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();

            posts = Arrays.asList(gson.fromJson(ja.toString(), Transformer[].class));

            ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
            list.add(putData("boiler"));
            list.add(putData("pipe"));
            list.add(putData("transformer"));
            list.add(putData("turbine"));*/

            return posts;
        }
        catch (Exception Ex)
        {

        }

        return null;
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getResources().openRawResource(R.raw.transformer);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tr_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
