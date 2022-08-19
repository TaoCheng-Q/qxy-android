package com.qxy.demo.entity.topList;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.topListEntity.Movie;

import java.util.List;

public class MovieList extends BaseObservable {
    private List<Movie> movieList;


    public MovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyChange();
    }
}
