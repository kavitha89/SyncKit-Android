package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.network.sync.basicsyncadapter.models.Turbine;
import com.example.android.network.sync.basicsyncadapter.util.Constants;


public class Turbine_details_activity extends Activity {

    Turbine turbineObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turbine_details_activity);

        try {
            if(getIntent().hasExtra("Turbine Object")) {
                Bundle data = getIntent().getExtras();
                this.turbineObject = (Turbine) data.getParcelable("Turbine Object");


                //Transformer transformerObject = (Transformer)getIntent().getSerializableExtra("Transformer Object");

                if (this.turbineObject !=null) {
                    //edit scenario, pre filling all text fields by getting each text field by id.
                    ((TextView) findViewById(R.id.edit_turbine_name)).setText(this.turbineObject.trbName);
                    ((TextView) findViewById(R.id.edit_turbine_location)).setText(this.turbineObject.trbLocation);
                    ((TextView) findViewById(R.id.edit_turbine_capacity)).setText(this.turbineObject.trbCapacity);
                    ((TextView) findViewById(R.id.edit_turbine_temp)).setText(this.turbineObject.trbTemp);
                    ((TextView) findViewById(R.id.edit_turbine_rotor_count)).setText(this.turbineObject.trbRotorCount);
                    ((TextView) findViewById(R.id.edit_turbine_rotation_speed)).setText(this.turbineObject.trbRotationSpeed);
                    ((TextView) findViewById(R.id.edit_turbine_RPM)).setText(this.turbineObject.trbCurrentRPM);
                    ((TextView) findViewById(R.id.edit_turbine_health_statusr)).setText(this.turbineObject.trbCompressorHealth);
                    ((TextView) findViewById(R.id.edit_turbine_oil_level)).setText(this.turbineObject.trbOilLevel);
                    ((TextView) findViewById(R.id.edit_turbine_pressure)).setText(this.turbineObject.trbPressure);

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
        getMenuInflater().inflate(R.menu.menu_turbine_details_activity, menu);
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


        else if(id == R.id.menu_save_turbine_details)
        {
            //save button has been pressed. handle accordingly.

            if(this.turbineObject == null)
            {

                //create new object
                this.turbineObject = new Turbine();
                this.turbineObject.trbName = ((TextView) findViewById(R.id.edit_turbine_name)).getText().toString();
                this.turbineObject.trbLocation = ((TextView) findViewById(R.id.edit_turbine_location)).getText().toString();
                this.turbineObject.trbCapacity = ((TextView) findViewById(R.id.edit_turbine_capacity)).getText().toString();
                this.turbineObject.trbTemp = ((TextView) findViewById(R.id.edit_turbine_temp)).getText().toString();
                this.turbineObject.trbRotorCount = ((TextView) findViewById(R.id.edit_turbine_rotor_count)).getText().toString();
                this.turbineObject.trbRotationSpeed = ((TextView) findViewById(R.id.edit_turbine_rotation_speed)).getText().toString();
                this.turbineObject.trbCurrentRPM = ((TextView) findViewById(R.id.edit_turbine_RPM)).getText().toString();
                this.turbineObject.trbCompressorHealth = ((TextView) findViewById(R.id.edit_turbine_health_statusr)).getText().toString();
                this.turbineObject.trbOilLevel = ((TextView) findViewById(R.id.edit_turbine_oil_level)).getText().toString();
                this.turbineObject.trbPressure = ((TextView) findViewById(R.id.edit_turbine_pressure)).getText().toString();

                this.turbineObject.syncStatus = Constants.SYNC_STATUS.INSERTED.getValue();
                if(this.turbineObject.insertObjectToDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Turbine Object Added Successfully", Toast.LENGTH_LONG).show();
                    this.finish();

                }
            }

            else
            {
                this.turbineObject.trbName = ((TextView) findViewById(R.id.edit_turbine_name)).getText().toString();
                this.turbineObject.trbLocation = ((TextView) findViewById(R.id.edit_turbine_location)).getText().toString();
                this.turbineObject.trbCapacity = ((TextView) findViewById(R.id.edit_turbine_capacity)).getText().toString();
                this.turbineObject.trbTemp = ((TextView) findViewById(R.id.edit_turbine_temp)).getText().toString();
                this.turbineObject.trbRotorCount = ((TextView) findViewById(R.id.edit_turbine_rotor_count)).getText().toString();
                this.turbineObject.trbRotationSpeed = ((TextView) findViewById(R.id.edit_turbine_rotation_speed)).getText().toString();
                this.turbineObject.trbCurrentRPM = ((TextView) findViewById(R.id.edit_turbine_RPM)).getText().toString();
                this.turbineObject.trbCompressorHealth = ((TextView) findViewById(R.id.edit_turbine_health_statusr)).getText().toString();
                this.turbineObject.trbOilLevel = ((TextView) findViewById(R.id.edit_turbine_oil_level)).getText().toString();
                this.turbineObject.trbPressure = ((TextView) findViewById(R.id.edit_turbine_pressure)).getText().toString();

                //update existing object via method in model class
                if(this.turbineObject.updateObjectInDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Turbine Object Updated Successfully", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
