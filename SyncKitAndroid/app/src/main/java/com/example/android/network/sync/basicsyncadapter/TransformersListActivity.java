package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.network.sync.basicsyncadapter.adpaters.TransformerListAdapter;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class TransformersListActivity extends Activity implements OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Menu mOptionsMenu;
    private List<Transformer> transformerList;
    private ListView listview;
    private TransformerListAdapter transformerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_list);
    }

    private void updateData()
    {
        listview = (ListView) findViewById(R.id.trListview);
        transformerList = Transformer.fetchAllAvailableObjectsInDB(getApplicationContext());
        transformerListAdapter = new TransformerListAdapter(this, R.layout.row, transformerList);
        listview .setAdapter(transformerListAdapter);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View v, int position,
                            long arg3)
    {
        Transformer transformerObejct = transformerList.get(position);
        if(!transformerObejct.equals(null))
        {
            Intent intent = new Intent(TransformersListActivity.this, Transformer_details_activity.class);
            intent.putExtra("Transformer Object",(Parcelable)transformerObejct);
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
                Transformer transformerObject = (Transformer)transformerList.get(position);
                transformerObject.deleteObject(getApplicationContext());
                transformerList.remove(position);
                transformerListAdapter.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.tr_list, menu);
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

        if(id == R.id.menu_add_new_transformer)
        {
            //go to add new transformer activity
            System.out.println("Clicked On Add");

            Intent intent = new Intent(this, Transformer_details_activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
