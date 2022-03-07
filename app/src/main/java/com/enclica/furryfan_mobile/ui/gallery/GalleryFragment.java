package com.enclica.furryfan_mobile.ui.gallery;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerview;
    private MyAdapter mAdapter;
    private View root;
    private SwipeRefreshLayout refresh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerview= root.findViewById(R.id.posts);
        mAdapter = new MyAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManger);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);

        refresh = root.findViewById(R.id.gallery_refresh);



        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refresh.setRefreshing(false);
            }
        });


        getData();
        return root;
    }

    private void getData() {

        itemList.clear();
        mAdapter.notifyDataSetChanged();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final SharedPreferences mSettings = getContext().getSharedPreferences("Login", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api?function=browse&num=30&token=" + mSettings.getString("token", ""), new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray allImageArray = new JSONArray(response);
                    if (allImageArray != null && allImageArray.length() > 0) {

                        ArrayList<ImageObject> imageObjects = new ArrayList<>();
                        for (int i = 0; i < allImageArray.length(); i++) {
                            JSONObject jsonItem = allImageArray.optJSONObject(i);
                            Log.d("tag", jsonItem.toString());
                                    Item item = new Item(jsonItem.optString("title"),
                                            jsonItem.get("author").toString(),
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
                    Log.e("error",e.toString());

                    Toast.makeText(getContext(), "A json error occured when loading the posts.   " + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "A server error occured when loading the posts.", Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }





}