package com.enclica.furryfan_mobile.internal.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enclica.furryfan_mobile.internal.items.CommissionItem;
import com.enclica.furryfan_mobile.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.ViewHolder> {

    public Button accept,decline,report;


    private List<CommissionItem>itemList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView refsheeturl, desc,send;
        public int approved = 0;
        public int reported = 0;
        public int comid = 0;

        public LinearLayout main;
        public Button accept,decline,report;

        final SharedPreferences mSettings = itemView.getContext().getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);

        public ViewHolder(View view) {
            super(view);

            // Define click listener for the ViewHolder's View

            refsheeturl = (TextView) view.findViewById(R.id.refsheet_itm);
            desc = (TextView) view.findViewById(R.id.commissiondescription_itm);
            send = (TextView) view.findViewById(R.id.name_itm);
            main = (LinearLayout) view.findViewById(R.id.main);
            accept = (Button) view.findViewById(R.id.btn1);
            decline = (Button) view.findViewById(R.id.btn2);
            report = (Button) view.findViewById(R.id.btn3);








            RequestQueue queue = Volley.newRequestQueue(this.main.getContext().getApplicationContext());

            accept.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //checks to see if approved by the user already if so mark as completed otherwise mark it as approved.
                    if(approved == 1){

                        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        Snackbar.make(v, "Marked as completed.", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        Snackbar.make(v, "Failed to mark commission as completed, please try again later.", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("function", "completecommission");
                                params.put("token", mSettings.getString("token", "t"));
                                params.put("id", Integer.toString(comid));
                                params.put("notnotify", "false");

                                return params;
                            }
                        };
                        queue.add(postRequest);



                    }else{





                        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        Snackbar.make(v, "Approved and started a new chat.", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        Snackbar.make(v, "We failed to approve the commission, please try again later.", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("function", "reportcommission");
                                params.put("token", mSettings.getString("token", "t"));
                                params.put("id", Integer.toString(comid));

                                return params;
                            }
                        };
                        queue.add(postRequest);



                    }


                }
            });
            decline.setOnClickListener(new AdapterView.OnClickListener() {

                @Override
                public void onClick(View v) {

                    StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    Snackbar.make(v, "Declined and removed from your account.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Snackbar.make(v, "An error occured when declining.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("function", "declinecommission");
                            params.put("token", mSettings.getString("token", "t"));
                            params.put("id", Integer.toString(comid));

                            return params;
                        }
                    };
                    queue.add(postRequest);

                    

                }
            });
            report.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {




                    StringRequest postRequest = new StringRequest(Request.Method.POST, "https://furryfan.net/api/",
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    Snackbar.make(v, "Reported to the furryfan team!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Snackbar.make(v, "An error occured when reporting to the furryfan Team, please try again later.", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("function", "reportcommission");
                            params.put("token", mSettings.getString("token", "t"));
                            params.put("id", Integer.toString(comid));

                            return params;
                        }
                    };
                    queue.add(postRequest);


                }
            });

        }
    }


    public CommissionAdapter(List<CommissionItem> dataSet) {
        itemList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.commission_layout, viewGroup, false);
        accept = (Button) view.findViewById(R.id.btn1);
        decline = (Button) view.findViewById(R.id.btn2);
        report = (Button) view.findViewById(R.id.btn3);




        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CommissionAdapter.ViewHolder holder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        CommissionItem row=itemList.get(position);


        holder.desc.setText("Description of commission\n"+row.getdescription());
        holder.refsheeturl.setText("Refsheet url\n"+row.getrefsheetURL());
        holder.send.setText(row.getsender());
        holder.approved = row.getApproved();
        holder.reported = row.getReported();
        holder.comid = row.getID();
        if(holder.reported == 1){
            itemList.remove(position);
            notifyItemRemoved(position);
        }
        if(holder.approved == 1) {
            holder.accept.setText("Mark as complete.");
            decline.setVisibility(View.INVISIBLE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
