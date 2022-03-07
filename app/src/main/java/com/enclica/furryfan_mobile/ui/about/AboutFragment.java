package com.enclica.furryfan_mobile.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enclica.furryfan_mobile.R;
import com.google.android.material.chip.Chip;


public class AboutFragment extends Fragment {


    private AboutViewModel aboutViewModel;
    public Chip twitter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        getActivity().setTitle("About Furryfan");

        twitter = (Chip) root.findViewById( R.id.twitter_btn );
        twitter.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/bluethefoxyt"));
                startActivity(browserIntent);
            };
        });
        return root;
    }
}