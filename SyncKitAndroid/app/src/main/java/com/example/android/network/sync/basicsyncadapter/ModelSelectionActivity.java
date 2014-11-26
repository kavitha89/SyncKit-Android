package com.example.android.network.sync.basicsyncadapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.network.sync.basicsyncadapter.models.Boiler;
import com.example.android.network.sync.basicsyncadapter.models.Pipe;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;
import com.example.android.network.sync.basicsyncadapter.models.Turbine;
import com.example.android.network.sync.basicsyncadapter.provider.SyncDatabaseHelper;
import com.example.android.network.sync.basicsyncadapter.util.Constants;

import java.util.ArrayList;


public class ModelSelectionActivity extends FragmentActivity {

    SyncDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_model_selection);

            ArrayList<Class> modelsRegisteredForSync = new ArrayList<Class>();
            modelsRegisteredForSync.add(Transformer.class);
            modelsRegisteredForSync.add(Boiler.class);
            modelsRegisteredForSync.add(Turbine.class);
            modelsRegisteredForSync.add(Pipe.class);

            dbHelper = SyncDatabaseHelper.getDataHelper(getApplicationContext(),modelsRegisteredForSync);
            dbHelper.getWritableDatabase();

        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_model_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
