package com.example.androiddemo.FragmentView.topListFragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddemo.R;
import com.example.androiddemo.viewmodels.topListViewmodels.TVViewModel;

public class TVFragment extends Fragment {

    private TVViewModel mViewModel;

    public static TVFragment newInstance() {
        return new TVFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_t_v, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        // TODO: Use the ViewModel
    }

}