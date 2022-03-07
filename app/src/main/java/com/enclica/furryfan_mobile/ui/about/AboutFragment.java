package com.enclica.furryfan_mobile.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enclica.furryfan_mobile.R;
import com.google.android.material.chip.Chip;

import org.w3c.dom.Text;


public class AboutFragment extends Fragment {


    private AboutViewModel aboutViewModel;
    public TextView secretmenu;
    public Chip twitter;
    public Button osl;
    private int counter = 0;
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

        osl = (Button) root.findViewById(R.id.osl);

        osl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OssLicensesMenuActivity.class));

            }
        });

        secretmenu = (TextView) root.findViewById(R.id.advanced_settings);

        secretmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if(counter>10){
                    Toast.makeText(getContext(), "Advanced settings activated.", Toast.LENGTH_LONG).show();
                }
            }
        });

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