package com.enclica.furryfan_mobile.ui.home;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.Color.parseColor;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.enclica.furryfan_mobile.internal.objects.ImageObject;
import com.enclica.furryfan_mobile.internal.items.Item;
import com.enclica.furryfan_mobile.internal.adapters.MyAdapter;
import com.enclica.furryfan_mobile.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
   private TextView usernametext;
   private ImageView profilepicture;
   private ImageView bg;
   private TextView bio;

   private String username;

   //post related
   private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerview;
    private MyAdapter mAdapter;

    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        usernametext = root.findViewById(R.id.username);
        bio = root.findViewById(R.id.biotext);
        profilepicture = root.findViewById(R.id.imageView2);
        bg = null;

        recyclerview= root.findViewById(R.id.userhomeposts);
        mAdapter = new MyAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManger);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);





        loaduserinfo();


        return root;


    }

    public void loaduserinfo() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final SharedPreferences mSettings = getContext().getSharedPreferences("Login", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api/?function=getuserinfofromtoken&token=" + mSettings.getString("token","t"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObject = new JSONObject(response);

                            usernametext.setText(jObject.getString("username"));
                            SharedPreferences.Editor editor;
                            editor = mSettings.edit();
                            editor.putString("username", jObject.getString("username"));
                            editor.commit();
                            bio.setText(jObject.getString("bio").replaceAll("\\[[^]]+]",""));
                            //profilepicture.setImageURL(new URI(jObject.getString("https://cdn.furryfan.net/art/" + jObject.getString("username") + "/data/pfp/" + jObject.getString("profilepicture")))); INVALID CODE STRUCTURE
                            Picasso.get().load("https://cdn.furryfan.net/art/" + jObject.getString("username") + "/data/pfp/" + jObject.getString("profilepicture")).into(profilepicture);
                            TextView header = getActivity().findViewById(R.id.header_title);
                            TextView subheader = getActivity().findViewById(R.id.header_subtitle);
                            LinearLayout iv = getActivity().findViewById(R.id.header);
                            ImageView imgv = getActivity().findViewById(R.id.imageView);
                            try{
                                header.setText(jObject.getString("username"));
                            }catch(Exception e){

                            }
                            subheader.setText(jObject.getString("role"));
                            username = jObject.getString("username");
                            Log.i("1",username);
                            loadposts(jObject.getString("username").toString());
                            Picasso.get().load("https://cdn.furryfan.net/art/" + jObject.getString("username") + "/data/pfp/" + jObject.getString("profilepicture").replace("_sm_400","_sm_50")).into(imgv);
                            switch(jObject.getString("role")){
                                case "":
                                    if(jObject.getInt("verified") == 1){
                                        subheader.setText("You are a verified user.");
                                    }else{
                                        subheader.setText(jObject.getString("Member"));
                                    }
                                    break;
                                default:
                                    subheader.setText("Staff role: " + jObject.getString("role"));
                                    break;
                            }

                            iv.setBackgroundColor(parseColor(jObject.getString("bannercolourhex").substring(0, jObject.getString("bannercolourhex").length() -2)));
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    public void loadposts(String Username){
        /* inililize posts */



        getData(username);
    }




    private void getData(String usernamee) {


        RequestQueue queue = Volley.newRequestQueue(this.getContext());
         SharedPreferences mSettings = getContext().getSharedPreferences("Login", MODE_PRIVATE);
         Log.i("FFLOG"," REQUEST URI: " + "https://furryfan.net/api?function=userbrowse&browsepage=1&username="+ usernamee +"&token=" + mSettings.getString("token", ""));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api?function=userbrowse&jd=1&browsepage=1&username="+ usernamee +"&token=" + mSettings.getString("token", ""), new Response.Listener<String>() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray allImageArray = new JSONArray(response);
                    if (allImageArray != null && allImageArray.length() > 0) {

                        ArrayList<ImageObject> imageObjects = new ArrayList<>();
                       // Log.d("tag", imageObjects.toString());
                        for (int i = 0; i < allImageArray.length(); i++) {
                            JSONObject jsonItem = allImageArray.optJSONObject(i);
                            Log.d("tag", jsonItem.toString());
                            Item item = new Item(
                                    jsonItem.optString("title"),
                                    jsonItem.getString("author"),
                                    jsonItem.getString("url"),
                                    jsonItem.getString("filetype"),
                                    jsonItem.getInt("ID"),
                                    jsonItem.getString("description"),
                                    jsonItem.getString("author"),
                                    jsonItem.getString("rating")
                            );

                            itemList.add(item);
                        }



                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                 //   Toast.makeText(getContext(), "A json error occured when loading the posts.   " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getContext(), "A server error occured when loading the posts.", Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
        Log.i("tl", stringRequest.getUrl());
    }





}
