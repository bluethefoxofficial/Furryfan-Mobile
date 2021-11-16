package com.enclica.furryfan_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class imageviewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewer);


        TextView txtRegister = (TextView) findViewById(R.id.vvusername);

        Intent secondIntent = getIntent( );

        //String message = "Selected course is " + secondIntent.getStringExtra("COURSE_SELECTED");

        TextView title = (TextView) findViewById(R.id.vptitle);
        TextView description = (TextView) findViewById(R.id.videoviewdescription);
        ImageView image = (ImageView) findViewById(R.id.imageviews);

        title.setText(secondIntent.getStringExtra("title"));
        description.setText(secondIntent.getStringExtra("description"));
        setTitle(secondIntent.getStringExtra("title"));
        Picasso.get().load(secondIntent.getStringExtra("url")).into(image);
        TextView un = (TextView) findViewById(R.id.vvusername);
        un.setText(secondIntent.getStringExtra("author"));

        txtRegister .setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myintent = new Intent(getApplicationContext(), Profile_page.class);
                myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myintent.putExtra("profile",secondIntent.getStringExtra("author"));
                getApplicationContext().startActivity(myintent);
            }


        });

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

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