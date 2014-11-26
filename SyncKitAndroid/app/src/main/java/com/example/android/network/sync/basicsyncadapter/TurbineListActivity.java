package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.network.sync.basicsyncadapter.adpaters.TurbineListAdapter;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;
import com.example.android.network.sync.basicsyncadapter.models.Turbine;
import java.util.List;


public class TurbineListActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Menu mOptionsMenu;

    private List<Turbine> turbineList;
    private ListView listview;
    private TurbineListAdapter turbineListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turbine_list);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        updateData();
    }


    private void updateData()
    {
        listview = (ListView) findViewById(R.id.turbineListView);
        turbineList = Turbine.fetchAllAvailableObjectsInDB(getApplicationContext());
        turbineListAdapter = new TurbineListAdapter(this, R.layout.row, turbineList);
        listview .setAdapter(turbineListAdapter);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.turbine_list, menu);
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

        else if(id == R.id.menu_add_new_turbine)
        {
            Intent intent = new Intent(this, Turbine_details_activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Turbine turbineObject = turbineList.get(position);
        if(!turbineObject.equals(null))
        {
            Intent intent = new Intent(TurbineListActivity.this, Turbine_details_activity.class);
            intent.putExtra("Turbine Object",(Parcelable)turbineObject);
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
                Turbine transformerObject = turbineList.get(position);
                transformerObject.deleteObject(getApplicationContext());
                turbineList.remove(position);
                turbineListAdapter.notifyDataSetChanged();
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

}
