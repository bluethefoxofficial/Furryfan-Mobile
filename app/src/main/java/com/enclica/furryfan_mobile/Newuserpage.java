package com.enclica.furryfan_mobile;

import static android.graphics.Color.parseColor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enclica.furryfan_mobile.pages.Settings;
import com.enclica.furryfan_mobile.pages.Upload_web_window;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Newuserpage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuserpage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loaduserinfo();
        //findViewById(R.id.advanced_settings).setVisibility(View.INVISIBLE);
        FloatingActionButton fab = findViewById(R.id.follow);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Upload_web_window.class));
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_chats)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    public void opensettings() {
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.newuserpage, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("ID: ", "" + item.getItemId());
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_feedback:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/UvEq2W2G8HbpwhLL8"));
                startActivity(browserIntent);
                break;
            // action with ID action_settings was selected
            case R.id.Shouts:
                opensettings();
                break;

            case R.id.logout:
                final SharedPreferences mSettings = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                mSettings.edit().clear().commit();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                super.finish();

                break;

            case 16908332:
                drawer.openDrawer(Gravity.LEFT);

                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void loaduserinfo() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final SharedPreferences mSettings = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api/?function=getuserinfofromtoken&token=" + mSettings.getString("token", "t"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObject = new JSONObject(response);

                            TextView header = Newuserpage.this.findViewById(R.id.header_title);
                            TextView subheader = Newuserpage.this.findViewById(R.id.header_subtitle);
                            LinearLayout iv = Newuserpage.this.findViewById(R.id.header);
                            ImageView imgv = Newuserpage.this.findViewById(R.id.imageView);
                            try {
                                header.setText(jObject.getString("username"));
                            } catch (Exception e) {
                                header.setText(jObject.getString("Furryfan network"));
                            }
                            subheader.setText(jObject.getString("role"));
                            Picasso.get().load("https://cdn.furryfan.net/art/" + jObject.getString("username") + "/data/pfp/" + jObject.getString("profilepicture").replace("_sm_400", "_sm_50")).into(imgv);
                            switch (jObject.getString("role")) {
                                case "":
                                    if (jObject.getInt("verified") == 1) {
                                        subheader.setText("You are a verified user.");
                                    } else {
                                        subheader.setText(jObject.getString("Member"));
                                    }
                                    break;
                                default:
                                    subheader.setText("Staff role: " + jObject.getString("role"));
                                    break;
                            }

                            iv.setBackgroundColor(parseColor(jObject.getString("bannercolourhex").substring(0, jObject.getString("bannercolourhex").length() - 2)));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
}