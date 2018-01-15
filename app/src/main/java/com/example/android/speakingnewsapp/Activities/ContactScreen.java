package com.example.android.speakingnewsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.speakingnewsapp.Adapters.CustomAdapter;
import com.example.android.speakingnewsapp.AppConfig;
import com.example.android.speakingnewsapp.AppController;
import com.example.android.speakingnewsapp.Preferences.PrefManager;
import com.example.android.speakingnewsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactScreen extends AppCompatActivity {

    public static final String TAG="tag";



    ArrayList<DataModel> dataModels;
    ListView listView;
    private CustomAdapter adapter;
    Button btn;
    static String str="";
    ImageView iv;
    //TextView texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btn=(Button)findViewById(R.id.send1);
        listView = (ListView) findViewById(R.id.listView);
        iv=(ImageView)findViewById(R.id.iv);
        //texts=(TextView) findViewById(R.id.texts);

        dataModels = new ArrayList<>();

        dataModels.add(new DataModel("Water", false));
        dataModels.add(new DataModel("Coffee", false));
        dataModels.add(new DataModel("Tea", false));
        dataModels.add(new DataModel("Blanket", false));

        adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);
                dataModel.checked = !dataModel.checked;
                adapter.notifyDataSetChanged();


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<dataModels.size(); i++){
                    if(dataModels.get(i).checked){
                        str=str+dataModels.get(i).name+" ";
                    }
                }
                Log.d(TAG, str);
                PrefManager prefManager;
                prefManager = new PrefManager(ContactScreen.this);
                String seatNumber = prefManager.getSeatNumber();
                Log.d("ContactScreen",seatNumber);
                sendRequest(seatNumber, str);
                //texts.setText(str);
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ContactScreen.this, message.class);
                startActivity(i);
            }
        });

    }

    private void sendRequest(final String seat_no, final String request_made) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {

                    Log.d("check","check1");
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    Log.d("check","check2");
                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(ContactScreen.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("seat_no", seat_no);
                params.put("request_made", request_made);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
