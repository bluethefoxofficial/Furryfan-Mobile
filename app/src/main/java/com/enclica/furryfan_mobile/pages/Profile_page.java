package com.enclica.furryfan_mobile.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enclica.furryfan_mobile.R;
import com.enclica.furryfan_mobile.databinding.ActivityProfilePageBinding;
import com.enclica.furryfan_mobile.internal.adapters.MyAdapter;
import com.enclica.furryfan_mobile.internal.items.Item;
import com.enclica.furryfan_mobile.internal.objects.ImageObject;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile_page extends AppCompatActivity {

    private ActivityProfilePageBinding binding;
    Intent secondIntent;
    FloatingActionButton fab;
    ActionBar actionBar;
    RecyclerView recyclerView;
    private List<Item> itemList = new ArrayList<>();
    Toolbar toolbar;
    MyAdapter mAdapter;
    SharedPreferences mSettings;
    private View root;
    public int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        secondIntent = getIntent( );




        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayoutProfile;
        setTitle(secondIntent.getStringExtra("profile"));
        toolBarLayout.setTitle(getTitle());
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mSettings = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);

        Log.i("Msettings-logger",mSettings.getAll().toString());



        root = View.inflate(getApplicationContext(), R.layout.content_scrolling, null);
        recyclerView = findViewById(R.id.b_contents);
        mAdapter = new MyAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(Profile_page.this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManger);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fab.setImageResource(R.drawable.ic_baseline_person_remove_24);

                if (fab.getTag().equals(R.drawable.ic_baseline_person_remove_24)) {
                    unfollow();
                    fab.setImageResource(R.drawable.ic_baseline_person_add_24);
                    fab.setTag(R.drawable.ic_baseline_person_add_24);

                }else{
                    follow();
                    fab.setImageResource(R.drawable.ic_baseline_person_remove_24);
                    fab.setTag(R.drawable.ic_baseline_person_remove_24);
                }


            }
        });


        loaduserinfo();
        loadposts();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return false;
    }



        public void unfollow(){

            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.i("FRESPONSE",response);
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Unfollowed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Failed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", mSettings.getString("token","token"));
                    params.put("userid", String.valueOf(id));
                    params.put("function", "unfollow");

                    return params;
                }
            };
        Log.i("k",mSettings.getString("token","token"));
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(postRequest);


        }

        public void follow(){


            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("FRESPONSE",response);
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Followed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Failed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", mSettings.getString("token","token"));
                    params.put("userid", String.valueOf(id));
                    params.put("function", "follow");

                    return params;
                }
            };
            Log.i("k",mSettings.getString("token","token"));
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(postRequest);


        }



    public void loaduserinfo() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        Log.i("WEB GET INFO ","https://furryfan.net/api/?function=getuserinfo&username=" + secondIntent.getStringExtra("profile"));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api/?function=getuserinfo&username=" + secondIntent.getStringExtra("profile"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObject = new JSONObject(response);

                            TextView bio = Profile_page.this.findViewById(R.id.profilebio);

                            ImageView profilepciture = Profile_page.this.findViewById(R.id.profilepictureprofile);
                            ImageView verified = Profile_page.this.findViewById(R.id.imageView5);

                            Log.i("t","This might work?");

                            bio.setText(jObject.getString("bio").replaceAll("\\[[^]]+]",""));
                            Picasso.get().load("https://cdn.furryfan.net/art/" + jObject.getString("username") + "/data/pfp/" + jObject.getString("profilepicture").replace("_sm_400", "_sm_200")).into(profilepciture);

                                    if (jObject.getInt("verified") == 1) {
                                       verified.setImageResource(R.drawable.ic_baseline_verified_24);
                                    }

                                    if(jObject.getString("followers").contains(mSettings.getString("username", "username"))){
                                        fab.setImageResource(R.drawable.ic_baseline_person_remove_24);

                                        fab.setTag(R.drawable.ic_baseline_person_remove_24);
                                    }else{

                                        fab.setTag(R.drawable.ic_baseline_person_add_24);
                                    }

                                    id = jObject.getInt("ID");
                                    Log.i("Un",mSettings.getString("username","username"));
                                    Log.i("json key", String.valueOf(jObject.getString("followers").contains(mSettings.getString("username","username"))));




                    try {
                        //ColorDrawable colorDrawable
                         //       = new ColorDrawable(Color.parseColor(jObject.getString("bannercolourhex").substring(0, jObject.getString("bannercolourhex").length() - 2)));
                       // actionBar.setBackgroundDrawable(
                           //     colorDrawable
                        //);
                        // toolbar.setBackground(colorDrawable);


                        // getSupportActionBar().setBackgroundDrawable(colorDrawable);
                    }catch (Exception e){


                    }

                    if(jObject.getString("followers").contains(mSettings.getString("username","username"))){
                        fab.setImageResource(R.drawable.ic_baseline_person_remove_24);
                        fab.setTag(R.drawable.ic_baseline_person_remove_24);
                        Log.i("FI", "1");
                    }else{
                        fab.setImageResource(R.drawable.ic_baseline_person_add_24);
                        fab.setTag(R.drawable.ic_baseline_person_add_24);
                        Log.i("FI", "0");
                    }



                            } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    //load posts of user

    public void loadposts(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences mSettings = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        Log.i("FFLOG"," REQUEST URI: " + "https://furryfan.net/api?function=userbrowse&browsepage=1&username="+ secondIntent.getStringExtra("profile") +"&token=" + mSettings.getString("token", ""));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api?function=userbrowse&jd=1&browsepage=1&username="+ secondIntent.getStringExtra("profile") +"&token=" + mSettings.getString("token", ""), new Response.Listener<String>() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray allImageArray = new JSONArray(response);
                    if (allImageArray != null && allImageArray.length() > 0) {

                        ArrayList<ImageObject> imageObjects = new ArrayList<>();
                         Log.d("tag", imageObjects.toString());
                        for (int i = 0; i < allImageArray.length(); i++) {
                            JSONObject jsonItem;
                            jsonItem = allImageArray.optJSONObject(i);
                            Log.d("tag", jsonItem.toString());
                            Item item = new Item(
                                    jsonItem.optString("title"),
                                    jsonItem.getString("author"),
                                    jsonItem.getString("url"),
                                    jsonItem.getString("filetype"),
                                    jsonItem.getInt("ID"),
                                    jsonItem.getString("description"),
                                    jsonItem.getString("author")
                            );

                            itemList.add(item);
                        }


                        Profile_page.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "A json error occured when loading the posts.   " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "A server error occured when loading the posts.", Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
        Log.i("tl", stringRequest.getUrl());
    }

}