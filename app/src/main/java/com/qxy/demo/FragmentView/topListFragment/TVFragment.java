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
import com.qxy.demo.room.dao.topListDao.ShowDao;
import com.qxy.demo.room.dao.topListDao.TelevisionDao;
import com.qxy.demo.room.entity.topListEntity.Show;
import com.qxy.demo.room.entity.topListEntity.Television;
import com.qxy.demo.utils.RoomUtil;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.TVViewModel;

import java.util.ArrayList;
import java.util.List;

public class TVFragment extends Fragment {

    private TopListViewModel mViewModel;

    private FragmentTVBinding binding;
    private TvAdapter adapter;
//    选择榜单版本的时候修改
    private int version=-1;

    private VersionAdapter versionAdapter;
//    记录版本游标默认为0
    private int cursor =0;
    //    版本列表显示数据
    private List<Integer> adapterVersionList = new ArrayList<>();
    //    榜单列表显示数据
    private List<Television> adapterTvList = new ArrayList<>();
//    实现版本选择接口
    private AdapterView.OnItemSelectedListener onItemSelectedListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//            选择版本后刷新数据
            version = (int) versionAdapter.getItem(i);
            adapterTvList.clear();
            loadTvs();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
//    实现榜单下拉刷新接口
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            下拉榜单后刷新数据
            binding.tvRecycleView.setRefreshing(false);
            adapterTvList.clear();
            loadTvs();
        }
    };


    public static TVFragment newInstance() {
        return new TVFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_t_v, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_t_v, container, false);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
//        榜单列表
        binding.tvList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TvAdapter(getContext());
        binding.tvList.setAdapter(adapter);
//        版本列表
        versionAdapter = new VersionAdapter(getContext());
        binding.tvVerSpinner.setAdapter(versionAdapter);

//        数据初始化
        loadTvs();
        loadVersion();
//        设置版本选择监听事件
        binding.tvVerSpinner.setOnItemSelectedListener(onItemSelectedListener);
//        设置榜单下拉刷新接口
        binding.tvRecycleView.setOnRefreshListener(onRefreshListener);
        return binding.getRoot();
    }
//加载版本列表
    private void loadVersion() {
        mViewModel.getVersionListMutableLiveData(2,cursor, RequestDouYin.PAGE_COUNT).observe(getViewLifecycleOwner(), new Observer<VersionList>() {
            @Override
            public void onChanged(VersionList versionList) {
//                数据加载成功刷新版本列表显示
                if(adapterVersionList.containsAll(versionList.getIntegerList())){
                    return;
                }
                adapterVersionList.addAll(versionList.getIntegerList());
                versionAdapter.setIntegerList(adapterVersionList);
                cursor=versionList.getCursor();
//                versionList.isHasMore();
            }
        });
    }

//    加载榜单数据
    private void loadTvs() {
        mViewModel.getTvListMutableLiveData(version).observe(getViewLifecycleOwner(), new Observer<TvList>() {
            @Override
            public void onChanged(TvList tvList) {
//                数据加载成功刷新榜单数据显示
                if (adapterTvList.containsAll(tvList.getTelevisionList())){
                    return;
                }
                adapterTvList.addAll(tvList.getTelevisionList());
                adapter.setTelevisionList(adapterTvList);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadVersion();
//        loadTvs();
    }
    @Override
    public void onPause() {
        super.onPause();
//        存放数据库
        if (!adapterTvList.isEmpty()){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    TelevisionDao tvsDao = RoomUtil.getInstance().getRoomInstance().getTelevisionDao();
                    tvsDao.deleteTelevisions();
                    for (Television m :
                            adapterTvList) {
                        tvsDao.insertTelevision(m);
                    }
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        // TODO: Use the ViewModel
    }

}