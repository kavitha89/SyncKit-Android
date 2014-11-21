package com.example.android.network.sync.basicsyncadapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.network.sync.basicsyncadapter.adpaters.PipeListAdapter;
import com.example.android.network.sync.basicsyncadapter.models.Pipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class PipeListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe);
        final ListView listview = (ListView) findViewById(R.id.pipeListView);

        List<Pipe> transformerList = buildData();
        String[] from = { Pipe.KEY_NAME,Pipe.KEY_LOCATION };
        int[] to = { android.R.id.text1,android.R.id.text2 };


        ListAdapter adapter = new PipeListAdapter(this, transformerList, android.R.layout.simple_list_item_2, new String[] {
                Pipe.KEY_NAME, Pipe.KEY_LOCATION }, new int[] { android.R.id.text1, android.R.id.text2 });
        listview.setAdapter(adapter);
    }

    private List<Pipe> buildData() {

        List<Pipe> transformers = new ArrayList<Pipe>();

        try {
            JSONObject jso = new JSONObject(loadJSONFromAsset());
            JSONArray ja = jso.getJSONArray("results");


            /*GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();*/
            //posts = Arrays.asList(gson.fromJson(ja.toString(), Transformer[].class));

            for( int i = 0; i < ja.length(); i++ ) {
                //Transformer transformerObject = new Transformer();
                transformers.add(new Pipe(ja.getJSONObject(i).getString("pipeLocation"), ja.getJSONObject(i).getString("pipeCurrentContainment")));
                //transformerObject.trsName = entry.getString("transformerNickName");
                //transformerObject.trsLocation = entry.getString("transformerLocation");
                //posts.add(transformerObject);
                //DO STUFF
            }
        }
        catch (Exception Ex)
        {
            Log.e("Machi Crash Log", "Failed to parse JSON due to: " + Ex);
        }

        return transformers;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getResources().openRawResource(R.raw.pipe);

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
        getMenuInflater().inflate(R.menu.pipe, menu);
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
