package com.example.android.network.sync.basicsyncadapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.network.sync.basicsyncadapter.util.Constants;


public class ModelSelectionActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println(Constants.SYNC_STATUS.CONFLICTED.getValue());
        System.out.println(Constants.SYNC_STATUS.SYNCED.getValue());
        System.out.println(Constants.SYNC_STATUS.DELETED.getValue());
        System.out.println(Constants.SYNC_STATUS.DIRTY.getValue());
        System.out.println(Constants.SYNC_STATUS.INSERTED.getValue());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_selection);
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
