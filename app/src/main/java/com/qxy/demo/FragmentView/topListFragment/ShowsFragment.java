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
import com.qxy.demo.databinding.FragmentShowsBinding;
import com.qxy.demo.entity.topList.ShowList;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.ShowsViewModel;

public class ShowsFragment extends Fragment {

    private TopListViewModel mViewModel;
    private FragmentShowsBinding binding;
    private ShowAdapter adapter;

    public static ShowsFragment newInstance() {
        return new ShowsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shows,container,false);
        binding.showsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShowAdapter(getContext());
        binding.showsList.setAdapter(adapter);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
        loadShows();
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_shows, container, false);
    }

    private void loadShows() {
        mViewModel.getShowListMutableLiveData(-1).observe(getViewLifecycleOwner(), new Observer<ShowList>() {
            @Override
            public void onChanged(ShowList showList) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ShowsViewModel.class);
        // TODO: Use the ViewModel
    }

}