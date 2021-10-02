package com.enclica.furryfan_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//import android.support.v7.app.AppCompatActivity;


public class usershome extends AppCompatActivity {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usershome);

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab2 = findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Newuserpage.class));
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });

        gridView = (GridView) findViewById(R.id.posts);
        getData();
        setTitle("Furryfan Gallery test weewoooweeewooo");
    }





        private void getData(){

            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api?function=browse&num=20", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONArray allImageArray = new JSONArray(response);
                        if(allImageArray != null && allImageArray.length() > 0){

                            ArrayList<ImageObject> imageObjects = new ArrayList<>();
                            for(int i = 0; i < allImageArray.length();i++){
                                JSONObject jsonItem = allImageArray.optJSONObject(i);

                                ImageObject io = new ImageObject();

                                io.setImageUrl(jsonItem.getString("url"));
                                io.setId(jsonItem.getInt("ID"));

                                imageObjects.add(io);
                            }

                           // myGridAdapter = new MyGridAdapter(usershome.this, imageObjects);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(),"A json error occured when loading the posts.",Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"A server error occured when loading the posts.",Toast.LENGTH_LONG).show();
                }
            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }
