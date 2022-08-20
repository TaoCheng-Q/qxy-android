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
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.MovieViewModel;

public class MovieFragment extends Fragment {

    private TopListViewModel mViewModel;
    private FragmentMovieBinding binding;
    private MovieAdapter adapter;
    private int version = -1;
    private VersionAdapter versionAdapter;
    private int cursor =0;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,false);
        adapter = new MovieAdapter(getContext());
        binding.movieList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.movieList.setAdapter(adapter);
        mViewModel = new ViewModelProvider(this).get(TopListViewModel.class);
        versionAdapter = new VersionAdapter(getContext());
        binding.movieVerSpinner.setAdapter(versionAdapter);

//        loadMovies();
//        loadVersion();
        binding.movieVerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                version = (int) versionAdapter.getItem(i);
                loadMovies();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.movieRecycleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.movieRecycleView.setRefreshing(false);
                loadMovies();
            }
        });

        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_movie, container, false);
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

    private void loadMovies() {
        mViewModel.getMovieListMutableLiveData(version).observe(getViewLifecycleOwner(), new Observer<MovieList>() {
            @Override
            public void onChanged(MovieList movieList) {
                adapter.setMovieList(movieList.getMovieList());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMovies();
        loadVersion();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
    }

}