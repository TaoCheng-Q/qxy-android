package com.qxy.demo.FragmentView.topListFragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.demo.R;
import com.qxy.demo.adapter.topListAdapter.ShowAdapter;
import com.qxy.demo.databinding.FragmentTVBinding;
import com.qxy.demo.entity.topList.TvList;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.TVViewModel;

public class TVFragment extends Fragment {

    private TopListViewModel mViewModel;

    private FragmentTVBinding binding;
    private ShowAdapter adapter;

    public static TVFragment newInstance() {
        return new TVFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_t_v, container, false);
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shows, container, false);
        binding.tvList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShowAdapter(getContext());
        binding.tvList.setAdapter(adapter);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
        loadShows();
        return binding.getRoot();
    }

    private void loadShows() {
        mViewModel.getTvListMutableLiveData(-1).observe(getViewLifecycleOwner(), new Observer<TvList>() {
            @Override
            public void onChanged(TvList tvList) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        // TODO: Use the ViewModel
    }

}