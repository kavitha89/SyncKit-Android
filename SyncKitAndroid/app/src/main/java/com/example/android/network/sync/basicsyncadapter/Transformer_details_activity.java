package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.network.sync.basicsyncadapter.models.Transformer;
import com.example.android.network.sync.basicsyncadapter.util.Constants;


public class Transformer_details_activity extends Activity {

    Transformer transformerObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformer_details_activity);


        try {
            if(getIntent().hasExtra("Transformer Object")) {
                Bundle data = getIntent().getExtras();
                System.out.println(data.getParcelable("Transformer Object"));
                this.transformerObject = (Transformer) data.getParcelable("Transformer Object");


                //Transformer transformerObject = (Transformer)getIntent().getSerializableExtra("Transformer Object");

                if (!this.transformerObject.equals(null)) {
                    //edit scenario, pre filling all text fields by getting each text field by id.
                    ((TextView) findViewById(R.id.edit_transformer_name)).setText(this.transformerObject.trsName);
                    ((TextView) findViewById(R.id.edit_transformer_location)).setText(this.transformerObject.trsLocation);
                    ((TextView) findViewById(R.id.edit_transformer_make)).setText(this.transformerObject.trsMake);
                    ((TextView) findViewById(R.id.edit_transformer_current_temp)).setText(this.transformerObject.trsCurrentTemp);
                    ((TextView) findViewById(R.id.edit_transformer_winding_count)).setText(this.transformerObject.trsWindingCount);
                    ((TextView) findViewById(R.id.edit_transformer_winding_make)).setText(this.transformerObject.trsWindingMake);
                    ((TextView) findViewById(R.id.edit_transformer_oil_level)).setText(this.transformerObject.trsOilLevel);
                    ((TextView) findViewById(R.id.edit_transformer_operating_power)).setText(this.transformerObject.trsOilLevel);
                    ((TextView) findViewById(R.id.edit_transformer_type)).setText(this.transformerObject.trsType);
                }
            }
            else {
                //create scenario, all text fields are left blank
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
        getMenuInflater().inflate(R.menu.menu_transformer_details_activity, menu);
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

        else if(id == R.id.menu_save_transformer_details)
        {
            //save button has been pressed. handle accordingly.

            if(this.transformerObject == null)
            {
                //create new object
                this.transformerObject = new Transformer();
                this.transformerObject.trsName = ((TextView) findViewById(R.id.edit_transformer_name)).getText().toString();
                this.transformerObject.trsLocation = ((TextView) findViewById(R.id.edit_transformer_location)).getText().toString();
                this.transformerObject.trsMake = ((TextView) findViewById(R.id.edit_transformer_make)).getText().toString();
                this.transformerObject.trsCurrentTemp = ((TextView) findViewById(R.id.edit_transformer_current_temp)).getText().toString();
                this.transformerObject.trsWindingCount = ((TextView) findViewById(R.id.edit_transformer_winding_count)).getText().toString();
                this.transformerObject.trsWindingMake = ((TextView) findViewById(R.id.edit_transformer_winding_make)).getText().toString();
                this.transformerObject.trsOilLevel = ((TextView) findViewById(R.id.edit_transformer_oil_level)).getText().toString();
                this.transformerObject.trsOperatingPower = ((TextView) findViewById(R.id.edit_transformer_operating_power)).getText().toString();
                this.transformerObject.trsType = ((TextView) findViewById(R.id.edit_transformer_type)).getText().toString();
                this.transformerObject.syncStatus = Constants.SYNC_STATUS.INSERTED.getValue();
                if(this.transformerObject.saveTransformerObjectToDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Transformer Object Added Successfully", Toast.LENGTH_LONG).show();
                    this.finish();

                }
            }

            else
            {
                this.transformerObject.trsName = ((TextView) findViewById(R.id.edit_transformer_name)).getText().toString();
                this.transformerObject.trsLocation = ((TextView) findViewById(R.id.edit_transformer_location)).getText().toString();
                this.transformerObject.trsMake = ((TextView) findViewById(R.id.edit_transformer_make)).getText().toString();
                this.transformerObject.trsCurrentTemp = ((TextView) findViewById(R.id.edit_transformer_current_temp)).getText().toString();
                this.transformerObject.trsWindingCount = ((TextView) findViewById(R.id.edit_transformer_winding_count)).getText().toString();
                this.transformerObject.trsWindingMake = ((TextView) findViewById(R.id.edit_transformer_winding_make)).getText().toString();
                this.transformerObject.trsOilLevel = ((TextView) findViewById(R.id.edit_transformer_oil_level)).getText().toString();
                this.transformerObject.trsOperatingPower = ((TextView) findViewById(R.id.edit_transformer_operating_power)).getText().toString();
                this.transformerObject.trsType = ((TextView) findViewById(R.id.edit_transformer_type)).getText().toString();
                //update existing object via method in model class
                if(this.transformerObject.updateTransformerObjectInDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Transformer Object Updated Successfully", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
