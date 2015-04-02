package com.example.android.androidwearjderobot;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class obstaclescreen extends Activity {

    private Spinner unitTypeSpinner;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obstaclescreen);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        // Fills the spinner with the unit options
        addItemsToUnitTypeSpinner();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_obstaclescreen, menu);
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

    public void addItemsToUnitTypeSpinner(){

        // Get a reference to the spinner
        unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> unitTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_obstacles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        unitTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        //unitTypeSpinner.setAdapter(unitTypeSpinnerAdapter);
    }


//    public void addListenerToUnitTypeSpinner() {
//        	        unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);
//        	        unitTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//            	            public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3)
//            	            {
//                	                // Get the item selected in the Spinner
//                	                String itemSelectedInSpinner = parent.getItemAtPosition(pos).toString();
//
//                	            }
//            	            public void onNothingSelected(AdapterView<?> arg0)
//            	            {
//                	                // TODO
//                	            }
//            	        });
//        	    }
}
