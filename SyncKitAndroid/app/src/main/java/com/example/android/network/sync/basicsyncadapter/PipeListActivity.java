package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
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

import com.example.android.network.sync.basicsyncadapter.adpaters.PipeListAdapter;
import com.example.android.network.sync.basicsyncadapter.adpaters.TransformerListAdapter;
import com.example.android.network.sync.basicsyncadapter.models.Pipe;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class PipeListActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Menu mOptionsMenu;
    private List<Pipe> pipeList;
    private ListView listview;
    private PipeListAdapter pipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe);

    }
    private void updateData()
    {
        listview = (ListView) findViewById(R.id.pipeListView);
        pipeList = Pipe.fetchAllAvailableObjectsInDB(getApplicationContext());
        pipeListAdapter = new PipeListAdapter(this, R.layout.row, pipeList);
        listview .setAdapter(pipeListAdapter);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View v, int position,
                            long arg3)
    {
        Pipe pipeObject = pipeList.get(position);
        if(!pipeObject.equals(null))
        {
            Intent intent = new Intent(PipeListActivity.this, Pipe_details_activity.class);
            intent.putExtra("Pipe Object",(Parcelable)pipeObject);
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
                Pipe pipeObject = (Pipe)pipeList.get(position);
                pipeObject.deleteObject(getApplicationContext());
                pipeList.remove(position);
                pipeListAdapter.notifyDataSetChanged();
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


        if(id == R.id.menu_add_new_pipe)
        {
            Intent intent = new Intent(this, Pipe_details_activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        updateData();
    }

}
