package com.example.android.network.sync.basicsyncadapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.network.sync.basicsyncadapter.adpaters.BoilerListAdapter;
import com.example.android.network.sync.basicsyncadapter.adpaters.TransformerListAdapter;
import com.example.android.network.sync.basicsyncadapter.models.Boiler;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class BoilerListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener  {

    ListView listview;
    ArrayList<Boiler> boilerList;
    BoilerListAdapter boilerListAdapter;
    Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boiler_list);
        listview = (ListView) findViewById(R.id.boilerListView);
    }

    private void updateData()
    {
        listview = (ListView) findViewById(R.id.trListview);
        //TODO
        //boilerList = Transformer.fetchAllAvailableObjectsInDB(getApplicationContext());
        boilerListAdapter = new BoilerListAdapter(this, R.layout.row, boilerList);
        listview .setAdapter(boilerListAdapter);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }

    /*private List<Boiler> buildData() {

        List<Boiler> transformers = new ArrayList<Boiler>();

        try {
            JSONObject jso = new JSONObject(loadJSONFromAsset());
            JSONArray ja = jso.getJSONArray("results");

            for( int i = 0; i < ja.length(); i++ ) {
                //Transformer transformerObject = new Transformer();
                transformers.add(new Boiler(ja.getJSONObject(i).getString("boilerName"), ja.getJSONObject(i).getString("boilerLocation")));
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

            InputStream is = getResources().openRawResource(R.raw.boiler);

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

    }*/

    @Override
    public void onItemClick(AdapterView<?> adapter, View v, int position,
                            long arg3)
    {
        Boiler boilerObject = boilerList.get(position);
        if(!boilerObject.equals(null))
        {
            Intent intent = new Intent(BoilerListActivity.this, Boiler_details_activity.class);
            intent.putExtra("Transformer Object",(Parcelable)boilerObject);
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder alert = new AlertDialog.Builder(
                this);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to delete record");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boiler boilerObject = (Boiler)boilerList.get(position);
                //TODO
                //boilerObject.deleteTransformerObject(getApplicationContext());
                boilerList.remove(position);
                boilerListAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.boiler_list, menu);
        return true;
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        updateData();
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

        if(id == R.id.menu_add_new_boiler)
        {
            //go to add new transformer activity
            System.out.println("Clicked On Add");

            Intent intent = new Intent(this, Boiler_details_activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
