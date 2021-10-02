package com.enclica.furryfan_mobile;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class Fragment_ff extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}