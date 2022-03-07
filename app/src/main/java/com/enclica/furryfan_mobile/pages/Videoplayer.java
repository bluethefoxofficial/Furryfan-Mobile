package com.enclica.furryfan_mobile.pages;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.enclica.furryfan_mobile.R;

public class Videoplayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        Intent secondIntent = getIntent( );

        //String message = "Selected course is " + secondIntent.getStringExtra("COURSE_SELECTED");

        TextView title = (TextView) findViewById(R.id.vptitle);
        TextView un = (TextView) findViewById(R.id.vvusername);

        TextView description = (TextView) findViewById(R.id.videoviewdescription);
        VideoView image = (VideoView) findViewById(R.id.videoplayer);

        title.setText(secondIntent.getStringExtra("title"));
        description.setText(secondIntent.getStringExtra("description"));
        un.setText(secondIntent.getStringExtra("author"));
       // Picasso.get().load(secondIntent.getStringExtra("url")).into(image);
        setTitle(secondIntent.getStringExtra("title"));

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(image);
        image.setMediaController(mediaController);
        image.setVideoURI(Uri.parse(secondIntent.getStringExtra("url")));
        image.start();
        image.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(1, 1);
            }
        });



        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        un.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myintent = new Intent(getApplicationContext(), Profile_page.class);
                myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myintent.putExtra("profile",secondIntent.getStringExtra("author"));
                getApplicationContext().startActivity(myintent);
            }


        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}