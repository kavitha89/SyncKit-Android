package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.network.sync.basicsyncadapter.models.Boiler;
import com.example.android.network.sync.basicsyncadapter.util.Constants;


public class Boiler_details_activity extends Activity {

    public Boiler boilerObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boiler_details_activity);

        try {
            if(getIntent().hasExtra("Boiler Object")) {
                Bundle data = getIntent().getExtras();
                this.boilerObject = data.getParcelable("Boiler Object");

                if (this.boilerObject != null) {
                    //edit scenario, pre filling all text fields by getting each text field by id.
                    ((TextView) findViewById(R.id.edit_boiler_name)).setText(this.boilerObject.bName);
                    ((TextView) findViewById(R.id.edit_boiler_location)).setText(this.boilerObject.bLocation);
                    ((TextView) findViewById(R.id.edit_boiler_capacity)).setText(this.boilerObject.bCapacity);
                    ((TextView) findViewById(R.id.edit_boiler_make)).setText(this.boilerObject.bMake);
                    ((TextView) findViewById(R.id.edit_boiler_temp)).setText(this.boilerObject.bTemp);
                    ((TextView) findViewById(R.id.edit_boiler_pressure)).setText(this.boilerObject.bPressure);
                    ((TextView) findViewById(R.id.edit_boiler_current_containment)).setText(this.boilerObject.bCurrentContainment);
                    ((TextView) findViewById(R.id.edit_boiler_health_status)).setText(this.boilerObject.bHealthStatus);
                }
            }
        }

        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boiler_details_activity, menu);
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

        else if(id == R.id.menu_save_boiler_details)
        {
            //save button has been pressed. handle accordingly.

            if(this.boilerObject == null)
            {
                //create new object
                this.boilerObject = new Boiler();
                this.boilerObject.bName = ((TextView) findViewById(R.id.edit_boiler_name)).getText().toString();
                this.boilerObject.bLocation = ((TextView) findViewById(R.id.edit_boiler_location)).getText().toString();
                this.boilerObject.bCapacity = ((TextView) findViewById(R.id.edit_boiler_capacity)).getText().toString();
                this.boilerObject.bPressure = ((TextView) findViewById(R.id.edit_boiler_pressure)).getText().toString();
                this.boilerObject.bTemp = ((TextView) findViewById(R.id.edit_boiler_temp)).getText().toString();
                this.boilerObject.bCurrentContainment = ((TextView) findViewById(R.id.edit_boiler_current_containment)).getText().toString();
                this.boilerObject.bMake = ((TextView) findViewById(R.id.edit_boiler_make)).getText().toString();
                this.boilerObject.bHealthStatus = ((TextView) findViewById(R.id.edit_boiler_health_status)).getText().toString();
                this.boilerObject.bMake = ((TextView) findViewById(R.id.edit_boiler_make)).getText().toString();
                this.boilerObject.syncStatus = Constants.SYNC_STATUS.INSERTED.getValue();
                if(this.boilerObject.insertObjectToDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Boiler Object Added Successfully", Toast.LENGTH_LONG).show();
                    this.finish();

                }
            }

            else
            {
                this.boilerObject.bName = ((TextView) findViewById(R.id.edit_boiler_name)).getText().toString();
                this.boilerObject.bLocation = ((TextView) findViewById(R.id.edit_boiler_location)).getText().toString();
                this.boilerObject.bCapacity = ((TextView) findViewById(R.id.edit_boiler_capacity)).getText().toString();
                this.boilerObject.bPressure = ((TextView) findViewById(R.id.edit_boiler_pressure)).getText().toString();
                this.boilerObject.bTemp = ((TextView) findViewById(R.id.edit_boiler_temp)).getText().toString();
                this.boilerObject.bCurrentContainment = ((TextView) findViewById(R.id.edit_boiler_current_containment)).getText().toString();
                this.boilerObject.bMake = ((TextView) findViewById(R.id.edit_boiler_make)).getText().toString();
                this.boilerObject.bHealthStatus = ((TextView) findViewById(R.id.edit_boiler_health_status)).getText().toString();
                this.boilerObject.bMake = ((TextView) findViewById(R.id.edit_boiler_make)).getText().toString();
                //update existing object via method in model class
                if(this.boilerObject.updateObjectInDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Boiler Object Updated Successfully", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
