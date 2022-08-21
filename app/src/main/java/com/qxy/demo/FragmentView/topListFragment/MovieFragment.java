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
import android.widget.ArrayAdapter;

import com.qxy.demo.R;
import com.qxy.demo.adapter.topListAdapter.MovieAdapter;
import com.qxy.demo.adapter.topListAdapter.VersionAdapter;
import com.qxy.demo.databinding.FragmentMovieBinding;
import com.qxy.demo.entity.topList.MovieList;
import com.qxy.demo.entity.topList.VersionList;
import com.qxy.demo.models.RequestDouYin;
import com.qxy.demo.room.dao.topListDao.MovieDao;
import com.qxy.demo.room.entity.topListEntity.Movie;
import com.qxy.demo.utils.RoomUtil;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    private TopListViewModel mViewModel;
    private FragmentMovieBinding binding;
    private MovieAdapter adapter;
//    记录版本号，默认为-1表示本周
    private int version = -1;
    private VersionAdapter versionAdapter;
//    分页游标默认为0；
    private int cursor =0;
//    版本列表显示数据
    private List<Integer> adapterVersionList = new ArrayList<>();
//    榜单列表显示数据
    private List<Movie> adapterMovieList = new ArrayList<>();
//    榜单选择事件接口实现
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//            选择后确定选择的版本信息，并且刷新榜单数据
            version = (int) versionAdapter.getItem(i);
            adapterMovieList.clear();
            loadMovies();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
//    榜单下拉刷新事件接口实现
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//                实现下拉刷新事件，刷新数据
            binding.movieRecycleView.setRefreshing(false);
            adapterMovieList.clear();
//            if(version!=-1){
//                version=-1;
//            }
            loadMovies();
        }
    };


    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,false);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
//        电影列表
        adapter = new MovieAdapter(getContext());
        binding.movieList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.movieList.setAdapter(adapter);
//        版本信息列表
        versionAdapter = new VersionAdapter(getContext());
        binding.movieVerSpinner.setAdapter(versionAdapter);

//        数据初始化
        loadMovies();
        loadVersion();
//        设置榜单版本选择事件监听接口
        binding.movieVerSpinner.setOnItemSelectedListener(onItemSelectedListener);

//        设置下拉刷新监听接口
        binding.movieRecycleView.setOnRefreshListener(onRefreshListener);

        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

//    加载版本列表信息
    private void loadVersion() {
        mViewModel.getVersionListMutableLiveData(1,cursor, RequestDouYin.PAGE_COUNT).observe(getViewLifecycleOwner(), new Observer<VersionList>() {
            @Override
            public void onChanged(VersionList versionList) {
//                信息加载成功后刷新数据显示
                adapterVersionList.addAll(versionList.getIntegerList());
                versionAdapter.setIntegerList(adapterVersionList);
//                记录当前版本列表页的游标
                cursor=versionList.getCursor();
//                versionList.isHasMore();
            }
        });
    }

//    加载电影榜单列表
    private void loadMovies() {
        mViewModel.getMovieListMutableLiveData(version).observe(getViewLifecycleOwner(), new Observer<MovieList>() {
            @Override
            public void onChanged(MovieList movieList) {
//                榜单数据刷新后更新榜单数据显示
                adapterMovieList.addAll(movieList.getMovieList());
                adapter.setMovieList(adapterMovieList);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadMovies();
//        loadVersion();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!adapterMovieList.isEmpty()){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MovieDao moviesDao = RoomUtil.getInstance().getRoomInstance().getMovieDao();
                    moviesDao.deleteMovies();
                    for (Movie m :
                            adapterMovieList) {
                        moviesDao.insertMovies(m);
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
//        mViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
    }

}