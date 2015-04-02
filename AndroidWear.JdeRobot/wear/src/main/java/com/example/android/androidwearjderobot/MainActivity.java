package com.example.android.androidwearjderobot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    private TextView mTextView;
//    Node mNode; // the connected device to send the message to
//    GoogleApiClient mGoogleApiClient;
    private static String nodeId = "";
    public static final String TAG = "Tag-Name";
    public static final long CONNECTION_TIME_OUT_MS = 10;
    private static final String HELLO_WORLD_WEAR_PATH = "/hello-world-wear";
    private String MESSAGE = "Hello";
    private Spinner unitTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //Connect the GoogleApiClient
//        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected(Bundle connectionHint) {
//                        Log.d(TAG, "onConnected: " + connectionHint);
//                        // Now you can use the data layer API
//                    }
//                    @Override
//                    public void onConnectionSuspended(int cause) {
//                        Log.d(TAG, "onConnectionSuspended: " + cause);
//                    }
//                })
//                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(ConnectionResult result) {
//                        Log.d(TAG, "onConnectionFailed: " + result);
//                    }
//                })
//                .addApi(Wearable.API)
//                .build();


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

    public void addItemsToUnitTypeSpinner(){

        // Get a reference to the spinner
	        unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> unitTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
	                R.array.spinner_types, android.R.layout.simple_spinner_item);
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

    /*
        Retrives GoogleApi Client
     */

    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }

    /*
        Connects to the first Node on Bluetooth
     */
    private void retrieveDeviceNode() {
        final GoogleApiClient client = getGoogleApiClient(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(client).await();
                List<Node> nodes = result.getNodes();
                if (nodes.size() > 0) {

                    nodeId = nodes.get(0).getId();
                }
                client.disconnect();
            }
        }).start();
    }

    public void CameraClick(View view) {
        Intent getNameScreenIntent = new Intent(MainActivity.this, cameraview.class);
        startActivity(getNameScreenIntent);

       // Toast.makeText(getApplicationContext(), "Clicked Cancel", Toast.LENGTH_SHORT).show();

    }
    public void SettingsClick(View view) {
        sendToast();
    }


    private void sendToast() {
        final GoogleApiClient client = getGoogleApiClient(this);
        if (nodeId != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                    Wearable.MessageApi.sendMessage(client, nodeId, MESSAGE, null);
                    client.disconnect();
                }
            }).start();
        }
        else {
            Toast.makeText(getApplicationContext(), "No nodes connected", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Send message to mobile handheld
     */
//    private void sendMessage() {
//
//        if (mNode != null && mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
//            Wearable.MessageApi.sendMessage(
//                    mGoogleApiClient, mNode.getId(), HELLO_WORLD_WEAR_PATH, null).setResultCallback(
//
//                    new ResultCallback<MessageApi.SendMessageResult>() {
//                        @Override
//                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
//
//                            if (!sendMessageResult.getStatus().isSuccess()) {
//                                Log.e("TAG", "Failed to send message with status code: "
//                                        + sendMessageResult.getStatus().getStatusCode());
//                            }
//                        }
//                    }
//            );
//        }else{
//
//        }
//
//    }
//    public void LayoutClick(View view) {
//
//    }
//
//    /*
//     * Resolve the node = the connected device to send the message to
//     */
//    private void resolveNode() {
//
//        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
//            @Override
//            public void onResult(NodeApi.GetConnectedNodesResult nodes) {
//                for (Node node : nodes.getNodes()) {
//                    mNode = node;
//                }
//            }
//        });
//        Toast.makeText(getApplicationContext(), "Clicked Cancel", Toast.LENGTH_SHORT).show();
//    }


}
