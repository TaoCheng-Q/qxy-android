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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.qxy.demo.R;
import com.qxy.demo.adapter.topListAdapter.ShowAdapter;
import com.qxy.demo.adapter.topListAdapter.VersionAdapter;
import com.qxy.demo.databinding.FragmentShowsBinding;
import com.qxy.demo.entity.topList.ShowList;
import com.qxy.demo.entity.topList.VersionList;
import com.qxy.demo.models.RequestDouYin;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.ShowsViewModel;

public class ShowsFragment extends Fragment {

    private TopListViewModel mViewModel;
    private FragmentShowsBinding binding;
    private ShowAdapter adapter;

    private int version=-1;
    private VersionAdapter versionAdapter;
    private int cursor =0;

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
        versionAdapter = new VersionAdapter(getContext());
        binding.showsVerSpinner.setAdapter(versionAdapter);
        loadShows();
        loadVersion();
        binding.showsVerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                version = (int) versionAdapter.getItem(i);
                loadShows();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.showRecycleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.showRecycleView.setRefreshing(false);
                loadShows();
            }
        });
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_shows, container, false);
    }

    private void loadVersion() {
        mViewModel.getVersionListMutableLiveData(2,cursor, RequestDouYin.PAGE_COUNT).observe(getViewLifecycleOwner(), new Observer<VersionList>() {
            @Override
            public void onChanged(VersionList versionList) {
                versionAdapter.setIntegerList(versionList.getIntegerList());
                cursor=versionList.getCursor();
//                versionList.isHasMore();
            }
        });
    }

    private void loadShows() {
        mViewModel.getShowListMutableLiveData(version).observe(getViewLifecycleOwner(), new Observer<ShowList>() {
            @Override
            public void onChanged(ShowList showList) {
                adapter.setShowList(showList.getShowList());

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