package com.enclica.furryfan_mobile.ui.commission;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enclica.furryfan_mobile.R;

public class CommissionFragment extends Fragment {

    private CommissionViewModel commissionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        commissionViewModel =
                new ViewModelProvider(this).get(CommissionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_commissions, container, false);
        getActivity().setTitle("Your commission requests");
        return root;

    }
}
