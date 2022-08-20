package com.qxy.demo.entity.topList;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.topListEntity.Movie;

import java.util.List;

public class MovieList extends BaseObservable {
    private List<Movie> movieList;
    private String active_time;



    public MovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public MovieList() {

    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyChange();
    }

    public String getActive_time() {
        return active_time;
    }

    public void setActive_time(String active_time) {
        this.active_time = active_time;
        notifyChange();
    }
}
