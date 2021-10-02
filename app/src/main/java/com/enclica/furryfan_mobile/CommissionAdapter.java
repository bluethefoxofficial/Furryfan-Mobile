package com.enclica.furryfan_mobile;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.MyViewHolder> {
    private List<Item>itemList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView icon;
        private LinearLayout main;
        private Intent myintent;
        public MyViewHolder(final View parent) {
            super(parent);
            title = (TextView) parent.findViewById(R.id.vptitle);
            subtitle = (TextView) parent.findViewById(R.id.commissiondescription);
            icon = (ImageView) parent.findViewById(R.id.icon);
            main = (LinearLayout) parent.findViewById(R.id.main);

            main.setOnClickListener(new AdapterView.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //  Toast.makeText(itemView.getContext(), "P-sPosition:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    Item itemvalue = itemList.get(getPosition());

                    switch(itemvalue.getFiletype())
                    {
                        case "mp4":
                            myintent = new Intent(parent.getContext(), Videoplayer.class);
                            myintent.putExtra("url",itemvalue.getImageURL());
                            myintent.putExtra("author",itemvalue.getAuthor());
                            myintent.putExtra("title",itemvalue.getTitle());
                            myintent.putExtra("description",itemvalue.getDescription());
                            parent.getContext().startActivity(myintent);
                            break;
                        case "mp3":
                            Toast.makeText(itemView.getContext(), "Non functional", Toast.LENGTH_SHORT).show();
                            break;
                        default:

                            myintent = new Intent(parent.getContext(), imageviewer.class);
                            myintent.putExtra("url",itemvalue.getImageURL());
                            myintent.putExtra("author",itemvalue.getAuthor());
                            myintent.putExtra("title",itemvalue.getTitle());
                            myintent.putExtra("description",itemvalue.getDescription());
                            parent.getContext().startActivity(myintent);

                            break;
                    }



                }

              /*  @Override
                public void onItemClick(AdapterView parent, View v) {
                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();

                } */
            });
        }
    }
    public CommissionAdapter(List<Item>itemList){
        this.itemList=itemList;
    }
    @Override
    public CommissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageitem,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item row=itemList.get(position);
        holder.title.setText(row.getTitle());
        holder.subtitle.setText(Html.fromHtml( row.getSubtitle()));
        // holder.icon.setImageURI(Picasso.get().load(row.getImageURL()); INVALID


        switch(row.getFiletype()){
            case "mp3":
                holder.icon.setImageResource(R.drawable.ic_baseline_music_note_24);
                break;
            case "mp4":
                holder.icon.setImageResource(R.drawable.ic_baseline_ondemand_video_24);
                break;
            default:
                Picasso.get().load(row.getImageURL()).into(holder.icon);
                break;

        }
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }
}