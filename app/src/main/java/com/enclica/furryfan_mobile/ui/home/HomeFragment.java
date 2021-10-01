package com.enclica.furryfan_mobile.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enclica.furryfan_mobile.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.Color.parseColor;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
   private TextView usernametext;
   private ImageView profilepicture;
   private ImageView bg;
   private TextView bio;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        usernametext = root.findViewById(R.id.username);
        bio = root.findViewById(R.id.biotext);
        profilepicture = root.findViewById(R.id.imageView2);
        bg = root.findViewById(R.id.background);

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
                            bio.setText(jObject.getString("bio").replaceAll("\\[[^]]+]",""));
                            //profilepicture.setImageURL(new URI(jObject.getString("https://cdn.furryfan.net/art/" + jObject.getString("username") + "/data/pfp/" + jObject.getString("profilepicture")))); INVALID CODE STRUCTURE
                            Picasso.get().load("https://cdn.furryfan.net/art/" + jObject.getString("username") + "/data/pfp/" + jObject.getString("profilepicture")).into(profilepicture);



                            bg.setBackgroundColor(parseColor(jObject.getString("bannercolourhex").substring(0, jObject.getString("bannercolourhex").length() -2)));




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

}
