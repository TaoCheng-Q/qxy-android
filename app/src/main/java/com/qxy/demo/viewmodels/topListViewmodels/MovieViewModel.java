package com.qxy.demo.viewmodels.topListViewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.demo.entity.topList.MovieList;

public class MovieViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<MovieList> movieListMutableLiveData;

    public MutableLiveData<MovieList> getMovieListMutableLiveData(){
        if(movieListMutableLiveData==null){
            movieListMutableLiveData=new MutableLiveData<>();
        }
//        网络获取电影排行榜列表

        return movieListMutableLiveData;
    }
}