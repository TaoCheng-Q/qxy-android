package com.qxy.demo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.demo.entity.topList.MovieList;
import com.qxy.demo.entity.topList.ShowList;
import com.qxy.demo.entity.topList.TvList;

public class TopListViewModel extends ViewModel {

    private MutableLiveData<MovieList> movieListMutableLiveData;

    private MutableLiveData<TvList> tvListMutableLiveData;

    private MutableLiveData<ShowList> showListMutableLiveData;

    public MutableLiveData<MovieList> getMovieListMutableLiveData(int version){
        if(movieListMutableLiveData==null){
            movieListMutableLiveData=new MutableLiveData<>();
        }
//        网络获取电影排行榜列表

        return movieListMutableLiveData;
    }

    public MutableLiveData<TvList> getTvListMutableLiveData(int version){
        if(tvListMutableLiveData==null){
            tvListMutableLiveData=new MutableLiveData<>();
        }
//        获取电视剧排行榜

        return tvListMutableLiveData;
    }

    public MutableLiveData<ShowList> getShowListMutableLiveData(int version){
        if (showListMutableLiveData==null){
            showListMutableLiveData=new MutableLiveData<>();
        }
//        获取show榜单

        return showListMutableLiveData;
    }
}
