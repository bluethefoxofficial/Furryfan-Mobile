package com.enclica.furryfan_mobile.internal.adapters;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.enclica.furryfan_mobile.internal.items.Item;
import com.enclica.furryfan_mobile.R;
import com.enclica.furryfan_mobile.pages.Videoplayer;
import com.enclica.furryfan_mobile.pages.imageviewer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Item>itemList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public String rating;
        public ImageView icon;
        private LinearLayout main;
        private Intent myintent;
        public ImageView ratingicon;
        public MyViewHolder(final View parent) {
            super(parent);
            title = (TextView) parent.findViewById(R.id.vptitle);
            subtitle = (TextView) parent.findViewById(R.id.commissiondescription_itm);
            icon = (ImageView) parent.findViewById(R.id.icon);
            main = (LinearLayout) parent.findViewById(R.id.main);
            ratingicon = (ImageView) parent.findViewById(R.id.Ratingsymbol);

            main.setOnClickListener(new AdapterView.OnClickListener() {

                @Override
                public void onClick(View v) {
                  //  Toast.makeText(itemView.getContext(), "P-sPosition:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    Item itemvalue = itemList.get(getPosition());

                    switch(itemvalue.getFiletype())
                    {
                        case "mp4":
                        case "mp3":
                        case "wav":

                            myintent = new Intent(parent.getContext(), Videoplayer.class);
                            myintent.putExtra("TEST","TEST");
                            myintent.putExtra("url",itemvalue.getImageURL());
                            myintent.putExtra("author",itemvalue.getAuthor());
                            myintent.putExtra("title",itemvalue.getTitle());

                            myintent.putExtra("description",itemvalue.getDescription());
                            myintent.putExtra("id",itemvalue.getPostID());

                            parent.getContext().startActivity(myintent);
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
    public MyAdapter(List<Item>itemList){
        this.itemList=itemList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageitem,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item row=itemList.get(position);
        holder.title.setText(row.getTitle());
        holder.subtitle.setText(Html.fromHtml( row.getSubtitle()));
       // holder.icon.setImageURI(Picasso.get().load(row.getImageURL()); INVALID
        holder.ratingicon.setVisibility(View.INVISIBLE);
        holder.rating = row.getRating();

        if(holder.rating.equals("Mature")){
            holder.ratingicon.setVisibility(View.VISIBLE);
        }
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