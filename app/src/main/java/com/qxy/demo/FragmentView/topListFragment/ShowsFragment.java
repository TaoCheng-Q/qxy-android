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
import com.qxy.demo.room.dao.topListDao.MovieDao;
import com.qxy.demo.room.dao.topListDao.ShowDao;
import com.qxy.demo.room.entity.topListEntity.Movie;
import com.qxy.demo.room.entity.topListEntity.Show;
import com.qxy.demo.utils.RoomUtil;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.ShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowsFragment extends Fragment {

    private TopListViewModel mViewModel;
    private FragmentShowsBinding binding;
    private ShowAdapter adapter;

//    记录榜单显示的版本，默认为-1，表示本周
    private int version=-1;
    private VersionAdapter versionAdapter;
//    记录榜单版本列表的页面游标
    private int cursor =0;
    //    需要显示的版本列表
    private List<Integer> adapterVersionList = new ArrayList<>();
//    需要显示的榜单列表
    private List<Show> adapterShowList = new ArrayList<>();
//    实现版本列表选择事件
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//            选择版本信息后刷新榜单数据
            version = (int) versionAdapter.getItem(i);
            adapterShowList.clear();
            loadShows();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
//    实现榜单列表下拉刷新事件
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//            下拉刷新后刷新榜单数据
            binding.showRecycleView.setRefreshing(false);
//            adapterVersionList.clear();
            adapterShowList.clear();
//            if(version!=-1){
//                version=-1;
//            }
            loadShows();
        }
    };



    public static ShowsFragment newInstance() {
        return new ShowsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shows,container,false);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
//        榜单列表
        binding.showsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShowAdapter(getContext());
        binding.showsList.setAdapter(adapter);
//        榜单版本列表
        versionAdapter = new VersionAdapter(getContext());
        binding.showsVerSpinner.setAdapter(versionAdapter);
//        数据初始化
        loadShows();
        loadVersion();
//        设置版本列表选择监听事件
        binding.showsVerSpinner.setOnItemSelectedListener(onItemSelectedListener);
//        设置榜单列表下拉刷新监听事件
        binding.showRecycleView.setOnRefreshListener(onRefreshListener);

        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_shows, container, false);
    }

//    加载版本数据
    private void loadVersion() {
        mViewModel.getVersionListMutableLiveData(3,cursor, RequestDouYin.PAGE_COUNT).observe(getViewLifecycleOwner(), new Observer<VersionList>() {
            @Override
            public void onChanged(VersionList versionList) {
//                数据获取成功后刷新版本数据
                adapterVersionList.addAll(versionList.getIntegerList());
                versionAdapter.setIntegerList(adapterVersionList);
                cursor=versionList.getCursor();
//                versionList.isHasMore();
            }
        });
    }

//    加载榜单数据
    private void loadShows() {
        mViewModel.getShowListMutableLiveData(version).observe(getViewLifecycleOwner(), new Observer<ShowList>() {
            @Override
            public void onChanged(ShowList showList) {
//                信息更新成功后刷新榜单显示
                adapterShowList.addAll(showList.getShowList());
                adapter.setShowList(adapterShowList);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadShows();
//        loadVersion();
    }

    @Override
    public void onPause() {
        super.onPause();
//        存放数据库
        if (!adapterShowList.isEmpty()){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ShowDao showsDao = RoomUtil.getInstance().getRoomInstance().getShowDao();
                    showsDao.deleteShows();
                    for (Show m :
                            adapterShowList) {
                        showsDao.insertShow(m);
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
//        mViewModel = new ViewModelProvider(this).get(ShowsViewModel.class);
        // TODO: Use the ViewModel
    }

}