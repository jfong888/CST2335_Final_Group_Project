package com.example.mesurmentconverter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class SoccerList extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "Soccer_List";
    //Some url endpoint that you may have
    String myUrl = " https://www.scorebat.com/video-api/v1/";
    BaseAdapter myAdapter;
    private ArrayList<SoccerObject> soccerList = new ArrayList<>();
    JSONObject resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soccer_layout);
        ListView theList = findViewById(R.id.live_soccermatch);
        theList.setAdapter(myAdapter = new SoccerListAdaptor(this, soccerList));

        Button search = findViewById(R.id.search);
        search.setOnClickListener(clik ->
        {
            try {
                soccerList.clear();
                SoccerService getRequest = new SoccerService();
                EditText edt = (EditText) findViewById(R.id.input_team);
                resp = getRequest.execute(myUrl + edt.getText().toString().toUpperCase()).get();
                for (Iterator<String> iter = resp.keys(); iter.hasNext(); ) {
                    String key = iter.next();
                    soccerList.add(new SoccerObject(key, resp.getString(key), edt.getText().toString().toUpperCase()));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            myAdapter.notifyDataSetChanged();
        });
    }

    // Function to convert ArrayList<String> to String[]
    public String[] getStringArray(ArrayList<String> arr) {

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // convert Object array to String array
        String[] str = Arrays
                .copyOf(objArr, objArr
                                .length,
                        String[].class);

        return str;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In Function onResume()");
    }

    @Override
    protected void onDestroy() {
        Log.e(ACTIVITY_NAME, "In Function onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.e(ACTIVITY_NAME, "In Function onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.e(ACTIVITY_NAME, "In Function onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(ACTIVITY_NAME, "In Function onStop()");
        super.onStop();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }






    public class SoccerService extends AsyncTask<String, Void, JSONObject> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        ProgressDialog p;



        @Override
        protected JSONObject doInBackground(String... params) {
            String stringUrl = params[0];
            JSONObject result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = new JSONObject(stringBuilder.toString()).getJSONObject("name");
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            } catch (JSONException e) {
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Toast toast = Toast.makeText(getApplicationContext(), "Successful load soccer matches", Toast.LENGTH_SHORT);
            toast.show();
            p.cancel();
        }
    }

}