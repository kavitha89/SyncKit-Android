package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
import android.content.ContentResolver;
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


public class TransformersListActivity extends Activity implements OnItemClickListener {

    private Menu mOptionsMenu;

    private List<Transformer> transformerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_list);


        /*ListAdapter adapter = new TransformerListAdapter(this, transformerList, android.R.layout.simple_list_item_2, new String[] {
                Transformer.KEY_NAME, Transformer.KEY_LOCATION }, new int[] { android.R.id.text1, android.R.id.text2 });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);*/
    }

    private void updateData()
    {
        final ListView listview = (ListView) findViewById(R.id.trListview);
        transformerList = Transformer.fetchAllTransformerObjectsInDB(this.getContentResolver());
        TransformerListAdapter customAdapter = new TransformerListAdapter(this, R.layout.row, transformerList);
        listview .setAdapter(customAdapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View v, int position,
                            long arg3)
    {
        Transformer transformerObejct = transformerList.get(position);
        if(!transformerObejct.equals(null))
        {
            System.out.println(transformerObejct.transformerID);

            Intent intent = new Intent(TransformersListActivity.this, Transformer_details_activity.class);
           // Bundle mBundle = new Bundle();
            //mBundle.putSerializable("Transformer Object",transformerObejct);
            //intent.putExtras(mBundle);
            intent.putExtra("Transformer Object",(Parcelable)transformerObejct);
            startActivity(intent);
        }
        // assuming string and if you want to get the value on click of list item
        // do what you intend to do on click of listview row
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
