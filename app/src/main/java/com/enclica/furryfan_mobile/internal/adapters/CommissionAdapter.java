package com.enclica.furryfan_mobile.internal.adapters;


import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enclica.furryfan_mobile.internal.items.CommissionItem;
import com.enclica.furryfan_mobile.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.ViewHolder> {

    private List<CommissionItem>itemList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView refsheeturl, desc,send;
        public LinearLayout main;
        public Button accept,decline,report;
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

            accept.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //android send a post request to a server using volly



                    Snackbar.make(v, "Accepted and opened a new chat!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
            decline.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {

                    
                    Snackbar.make(v, "Declined and removed from your account.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            report.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Reported to the furryfan team!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
