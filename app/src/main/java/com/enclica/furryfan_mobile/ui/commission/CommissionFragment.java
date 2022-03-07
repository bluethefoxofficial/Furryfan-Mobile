package com.enclica.furryfan_mobile.ui.commission;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
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
import androidx.lifecycle.ViewModel;
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
import com.enclica.furryfan_mobile.internal.adapters.CommissionAdapter;
import com.enclica.furryfan_mobile.internal.items.CommissionItem;
import com.enclica.furryfan_mobile.internal.objects.ImageObject;
import com.enclica.furryfan_mobile.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommissionFragment extends Fragment {
    private CommissionViewModel commissionViewModel;
    private SwipeRefreshLayout refresh;
    private final List<CommissionItem> itemList = new ArrayList<>();
    private RecyclerView recyclerview;
    private CommissionAdapter comAdapter;
    private View root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        commissionViewModel = new ViewModelProvider(this).get(CommissionViewModel.class);

        getActivity().setTitle("Your COMMISSIONS");
        root = inflater.inflate(R.layout.fragment_commissions, container, false);
        recyclerview= root.findViewById(R.id.commissions_view);
        comAdapter = new CommissionAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getContext());

        recyclerview.setItemAnimator(new DefaultItemAnimator());

        recyclerview.setAdapter(comAdapter);

        recyclerview.setLayoutManager(mLayoutManger);

        recyclerview.setItemAnimator(new DefaultItemAnimator());


        refresh = root.findViewById(R.id.swiperefresh);

        


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

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final SharedPreferences mSettings = getContext().getSharedPreferences("Login", MODE_PRIVATE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://furryfan.net/api?function=getcommissions&token=" + mSettings.getString("token", ""), new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                   itemList.clear();
                   comAdapter.notifyDataSetChanged();

                    JSONArray allImageArray = new JSONArray(response);
                    if (allImageArray != null && allImageArray.length() > 0) {

                        ArrayList<ImageObject> imageObjects = new ArrayList<>();
                        for (int i = 0; i < allImageArray.length(); i++) {
                            JSONObject jsonItem = allImageArray.optJSONObject(i);
                            Log.d("tag", jsonItem.toString());
                            CommissionItem item = new CommissionItem(
                                    jsonItem.getString("refsheeturl"),
                                    jsonItem.optString("description"),
                                    jsonItem.getString("sender"),
                                    jsonItem.getInt("ID"),
                                    jsonItem.getInt("approved"),
                                    jsonItem.getInt("complete"),
                                    jsonItem.getInt("reported")
                            );

                            itemList.add(item);
                        }
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                comAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("error",e.toString());
                    View rootView = ((Activity)getContext()).getWindow().getDecorView().findViewById(android.R.id.content);


                    Snackbar.make(rootView, "No commissions at this current time.", Snackbar.LENGTH_LONG).show();
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
        Log.d("tl", stringRequest.getUrl());
    }
}
