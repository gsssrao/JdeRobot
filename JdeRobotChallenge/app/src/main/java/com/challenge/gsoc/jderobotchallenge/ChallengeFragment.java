package com.challenge.gsoc.jderobotchallenge;

/**
 * Created by MAC on 22/03/15.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Encapsulates fetching the data and displaying it as a {@link ListView} layout.
 */
public class ChallengeFragment extends Fragment {


    ListView listView;
    ArrayList<String> rv;       //List for Amount
    ArrayList<String> rv2;      //List for Currency

    private ArrayAdapter<String> mChallengeAdapter;

    public ChallengeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.challengefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Refresh Button Usage
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchTask Task = new FetchTask();
            Task.execute("94043");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Data for the ListView

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String[] data = {

        };
        List<String> listchallenge = new ArrayList<String>(Arrays.asList(data));

        // The ArrayAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        mChallengeAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_challenge, // The name of the layout ID.
                        R.id.list_item_challenge_textview, // The ID of the textview to populate.
                        listchallenge);


        // Get a reference to the ListView, and attach this adapter to it.
        listView = (ListView) view.findViewById(R.id.listview_challenge);
        listView.setAdapter(mChallengeAdapter);

        // Launching new screen on Selecting Single ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Log.d(rv.get(position),rv2.get(position));

                // Starting new intent
                Intent in = new Intent(getActivity().getApplicationContext(), Display.class);
                in.putExtra("Amount", rv.get(position));
                in.putExtra("Currency", rv2.get(position));
                getActivity().startActivity(in);

            }
        });

        rv = new ArrayList<String>();
        rv2 = new ArrayList<String>();

        FetchTask Task = new FetchTask();
        Task.execute("94043");


    }

    public class FetchTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchTask.class.getSimpleName();

        private String[] getDataFromJson(String JsonStr)
                throws JSONException {

            final String dummy = "";

            JSONArray jArray = new JSONArray(JsonStr);

            String[] resultStrs = new String[jArray.length()];

            for(int i=0; i< jArray.length(); i++){
                JSONObject jobj = jArray.getJSONObject(i);
                resultStrs[i] = jobj.getString("sku");
                rv.add(i,jobj.getString("amount"));
                rv2.add(i,jobj.getString("currency"));
            }
            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Entries: " + s);
            }
            return resultStrs;

        }
        @Override
        protected String[] doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String JsonStr = null;

            String format = "json";
            String units = "metric";

            try {
                URL url = new URL("http://quiet-stone-2094.herokuapp.com/transactions.json");


                // Create the request to URL, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                Reader reader1 = new InputStreamReader(inputStream);
                BufferedReader r = new BufferedReader(reader1);
                StringBuffer buffer = new StringBuffer();
                String line ;
                while((line=r.readLine()) != null){
                    buffer.append(line + "\n");

                }

                Log.i("ResponseData" , ""+buffer);

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                JsonStr = buffer.toString();
                Log.v(LOG_TAG, "JSON string: " + JsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getDataFromJson(JsonStr);
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the data.
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mChallengeAdapter.clear();
                for(String dayChallengeStr : result) {
                    mChallengeAdapter.add(dayChallengeStr);
                }


                // New data is back from the server.
            }
        }

    }


}