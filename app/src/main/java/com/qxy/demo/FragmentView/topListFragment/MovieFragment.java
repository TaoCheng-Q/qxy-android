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
import com.qxy.demo.adapter.topListAdapter.MovieAdapter;
import com.qxy.demo.databinding.FragmentMovieBinding;
import com.qxy.demo.entity.topList.MovieList;
import com.qxy.demo.viewmodels.TopListViewModel;
import com.qxy.demo.viewmodels.topListViewmodels.MovieViewModel;

public class MovieFragment extends Fragment {

    private TopListViewModel mViewModel;
    private FragmentMovieBinding binding;
    private MovieAdapter adapter;

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

        loadMovies();

        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    private void loadMovies() {
        mViewModel.getMovieListMutableLiveData(-1).observe(getViewLifecycleOwner(), new Observer<MovieList>() {
            @Override
            public void onChanged(MovieList movieList) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
    }

}