package com.example.android.network.sync.basicsyncadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.network.sync.basicsyncadapter.models.Boiler;
import com.example.android.network.sync.basicsyncadapter.models.Pipe;
import com.example.android.network.sync.basicsyncadapter.util.Constants;


public class Pipe_details_activity extends Activity {

    public Pipe pipeObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_details_activity);

        try {
            if(getIntent().hasExtra("Pipe Object")) {
                Bundle data = getIntent().getExtras();
                this.pipeObject = data.getParcelable("Pipe Object");

                if (this.pipeObject != null) {
                    //edit scenario, pre filling all text fields by getting each text field by id.
                    ((TextView) findViewById(R.id.edit_pipe_diamter)).setText(this.pipeObject.pDiameter);
                    ((TextView) findViewById(R.id.edit_pipe_location)).setText(this.pipeObject.pLocation);
                    ((TextView) findViewById(R.id.edit_pipe_length)).setText(this.pipeObject.pLength);
                    ((TextView) findViewById(R.id.edit_pipe_make)).setText(this.pipeObject.pMake);
                    ((TextView) findViewById(R.id.edit_pipe_health_status)).setText(this.pipeObject.pHealthStatus);
                    ((TextView) findViewById(R.id.edit_pipe_containment)).setText(this.pipeObject.pCurrentContainment);
                    ((TextView) findViewById(R.id.edit_pipe_pressure)).setText(this.pipeObject.pPressure);
                    ((TextView) findViewById(R.id.edit_pipe_temp)).setText(this.pipeObject.pTemperature);
                    ((TextView) findViewById(R.id.edit_pipe_max_pressure)).setText(this.pipeObject.pMaxPressure);
                    ((TextView) findViewById(R.id.edit_pipe_max_temp)).setText(this.pipeObject.pMaxTemperature);
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
        getMenuInflater().inflate(R.menu.menu_pipe_details_activity, menu);
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


        else if(id == R.id.menu_save_pipe_details)
        {
        //save button has been pressed. handle accordingly.

            if(((TextView) findViewById(R.id.edit_pipe_diamter)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_location)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_length)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_make)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_health_status)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_containment)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_pressure)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_temp)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_max_pressure)).getText().toString().length() <=0 || ((TextView) findViewById(R.id.edit_pipe_max_temp)).getText().toString().length() <=0)
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        this);
                alert.setTitle("Alert!!");
                alert.setMessage("Enter All Values");
                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return super.onOptionsItemSelected(item);
            }

            if(this.pipeObject == null)
            {

                //create new object
                this.pipeObject = new Pipe();
                this.pipeObject.pDiameter = ((TextView) findViewById(R.id.edit_pipe_diamter)).getText().toString();
                this.pipeObject.pLocation = ((TextView) findViewById(R.id.edit_pipe_location)).getText().toString();
                this.pipeObject.pLength = ((TextView) findViewById(R.id.edit_pipe_length)).getText().toString();
                this.pipeObject.pMake = ((TextView) findViewById(R.id.edit_pipe_make)).getText().toString();
                this.pipeObject.pHealthStatus = ((TextView) findViewById(R.id.edit_pipe_health_status)).getText().toString();
                this.pipeObject.pCurrentContainment = ((TextView) findViewById(R.id.edit_pipe_containment)).getText().toString();
                this.pipeObject.pPressure = ((TextView) findViewById(R.id.edit_pipe_pressure)).getText().toString();
                this.pipeObject.pTemperature = ((TextView) findViewById(R.id.edit_pipe_temp)).getText().toString();
                this.pipeObject.pMaxPressure = ((TextView) findViewById(R.id.edit_pipe_max_pressure)).getText().toString();
                this.pipeObject.pMaxTemperature = ((TextView) findViewById(R.id.edit_pipe_max_temp)).getText().toString();

                this.pipeObject.syncStatus = Constants.SYNC_STATUS.INSERTED.getValue();
                if(this.pipeObject.insertObjectToDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Boiler Object Added Successfully", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }

            else
            {
                this.pipeObject.pDiameter = ((TextView) findViewById(R.id.edit_pipe_diamter)).getText().toString();
                this.pipeObject.pLocation = ((TextView) findViewById(R.id.edit_pipe_location)).getText().toString();
                this.pipeObject.pLength = ((TextView) findViewById(R.id.edit_pipe_length)).getText().toString();
                this.pipeObject.pMake = ((TextView) findViewById(R.id.edit_pipe_make)).getText().toString();
                this.pipeObject.pHealthStatus = ((TextView) findViewById(R.id.edit_pipe_health_status)).getText().toString();
                this.pipeObject.pCurrentContainment = ((TextView) findViewById(R.id.edit_pipe_containment)).getText().toString();
                this.pipeObject.pPressure = ((TextView) findViewById(R.id.edit_pipe_pressure)).getText().toString();
                this.pipeObject.pTemperature = ((TextView) findViewById(R.id.edit_pipe_temp)).getText().toString();
                this.pipeObject.pMaxPressure = ((TextView) findViewById(R.id.edit_pipe_max_pressure)).getText().toString();
                this.pipeObject.pMaxTemperature = ((TextView) findViewById(R.id.edit_pipe_max_temp)).getText().toString();

                //update existing object via method in model class
                if(this.pipeObject.updateObjectInDB(getApplicationContext()))
                {
                    Toast.makeText(this, "Boiler Object Updated Successfully", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }

        }


        return super.onOptionsItemSelected(item);
    }
}
