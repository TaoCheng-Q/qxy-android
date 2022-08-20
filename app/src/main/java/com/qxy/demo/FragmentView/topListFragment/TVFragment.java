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
import com.qxy.demo.adapter.topListAdapter.TvAdapter;
import com.qxy.demo.adapter.topListAdapter.VersionAdapter;
import com.qxy.demo.databinding.FragmentTVBinding;
import com.qxy.demo.entity.topList.TvList;
import com.qxy.demo.entity.topList.VersionList;
import com.qxy.demo.models.RequestDouYin;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.TVViewModel;

public class TVFragment extends Fragment {

    private TopListViewModel mViewModel;

    private FragmentTVBinding binding;
    private TvAdapter adapter;
//    选择榜单版本的时候修改
    private int version=-1;

    private VersionAdapter versionAdapter;
    private int cursor =0;

    public static TVFragment newInstance() {
        return new TVFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_t_v, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_t_v, container, false);
        binding.tvList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TvAdapter(getContext());
        binding.tvList.setAdapter(adapter);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
        versionAdapter = new VersionAdapter(getContext());

        binding.tvVerSpinner.setAdapter(versionAdapter);

//        loadTvs();
//        loadVersion();
        binding.tvVerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                version = (int) versionAdapter.getItem(i);
                loadTvs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.tvRecycleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.tvRecycleView.setRefreshing(false);
                loadTvs();
            }
        });
        return binding.getRoot();
    }

    private void loadVersion() {
        mViewModel.getVersionListMutableLiveData(1,cursor, RequestDouYin.PAGE_COUNT).observe(getViewLifecycleOwner(), new Observer<VersionList>() {
            @Override
            public void onChanged(VersionList versionList) {
                versionAdapter.setIntegerList(versionList.getIntegerList());
                cursor=versionList.getCursor();
//                versionList.isHasMore();
            }
        });
    }

    private void loadTvs() {
        mViewModel.getTvListMutableLiveData(version).observe(getViewLifecycleOwner(), new Observer<TvList>() {
            @Override
            public void onChanged(TvList tvList) {
                adapter.setTelevisionList(tvList.getTelevisionList());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadVersion();
        loadTvs();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        // TODO: Use the ViewModel
    }

}