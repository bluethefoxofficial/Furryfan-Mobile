package com.enclica.furryfan_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import android.support.v7.appcomta;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rellay1, rellay2;
    EditText username;
    ActionBar actionBar;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            final SharedPreferences mSettings = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
            if(mSettings.contains("token")){

                startActivity(new Intent(getApplicationContext(), Newuserpage.class));
                finish();

            }
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getApplicationContext());
            dlgAlert.setMessage("This app is experimental and will change quickly you're essentially an alpha tester.");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        actionBar = getSupportActionBar();
        actionBar.hide();
        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

    }
    //login verification code
    public void login() {
        final SharedPreferences mSettings = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        final TextView tv = findViewById(R.id.tv_login2);
        final EditText username = findViewById(R.id.login_username);
        final EditText password = findViewById(R.id.login_password);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        try {
                            JSONObject jObject = new JSONObject(response);


                            if (!jObject.getBoolean("error")) {
                                SharedPreferences.Editor editor;
                                editor = mSettings.edit();
                                editor.putString("token", jObject.getString("token"));
                                editor.putString("username",username.getText().toString());

                                editor.commit();

                                startActivity(new Intent(getApplicationContext(), Newuserpage.class));
                                finish();
                            } else {

                                Snackbar.make(getWindow().getDecorView().getRootView(), "Failed to login. Please check your username and password and try again.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }
                        }catch(JSONException e){
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Failed to login JSON response error", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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
                params.put("password", password.getText().toString());
                params.put("function", "login");

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);
    }


    public void buttonClicked(View view) {

        if (view.getId() == R.id.accountrecovery) {
         //   startActivity(new Intent(getApplicationContext(), accountrecovery.class));
        } else if (view.getId() == R.id.signup) {

            startActivity(new Intent(getApplicationContext(), signup.class));
        } else if (view.getId() == R.id.logintoff) {
            login();
        }

    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}