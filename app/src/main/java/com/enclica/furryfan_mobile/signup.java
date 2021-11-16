package com.enclica.furryfan_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }


    protected void signup(){
        EditText email = findViewById(R.id.signup_email);
        EditText username = findViewById(R.id.signup_username);
        EditText password = findViewById(R.id.signup_password);

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        try {
                            JSONArray jObject = new JSONArray(response);


                            if (!jObject.getJSONObject(0).getBoolean("error")) {

                            } else {
                                JSONObject jsonObject = new JSONObject(response);
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Failed to login. Please check your username and password and try again.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }
                        }catch(JSONException e){


                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Your furryfan account has been created!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();



                            } catch (JSONException jsonException) {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Failed to login JSON response error" + e.getMessage(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Failed to login server connection error", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("function", "register");

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);



    }

}