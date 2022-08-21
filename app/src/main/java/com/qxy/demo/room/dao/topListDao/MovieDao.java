package com.qxy.demo.room.dao.topListDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.qxy.demo.room.entity.topListEntity.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    /**
     * 电影数据插入
     * @param movies
     */
    @Insert
    void insertMovies(Movie... movies);

    /**
     * 数据查询
     * @return
     */
    @Query("SELECT * FROM Movie")
    List<Movie> queryMovies();

    /**
     * 数据删除
     */
    @Query("DELETE FROM Movie")
    void deleteMovies();
}
