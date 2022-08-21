package com.qxy.demo.room.DataBases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.qxy.demo.room.dao.DouYinUserDao;
import com.qxy.demo.room.dao.EmotionDao;
import com.qxy.demo.room.dao.WeiBoMessageDao;
import com.qxy.demo.room.dao.topListDao.MovieDao;
import com.qxy.demo.room.dao.topListDao.ShowDao;
import com.qxy.demo.room.dao.topListDao.TelevisionDao;
import com.qxy.demo.room.entity.DouYinUser;
import com.qxy.demo.room.entity.Emotion;
import com.qxy.demo.room.entity.WeiBoMessage;
import com.qxy.demo.room.entity.topListEntity.Movie;
import com.qxy.demo.room.entity.topListEntity.Show;
import com.qxy.demo.room.entity.topListEntity.Television;

@Database(entities = {Emotion.class, WeiBoMessage.class, Movie.class, Show.class, Television.class, DouYinUser.class},version = 1)
public abstract class MyDataBase extends RoomDatabase {

    /**
     * 微博表情数据库操作类
     * @return
     */
    public abstract EmotionDao getEmotionDao();

    /**
     * 微博极简短消息数据库操作类
     * @return
     */
    public abstract WeiBoMessageDao getWeiBoMessageDao();

    /**
     * 获取电影表单操作类
     * @return
     */
    public abstract MovieDao getMovieDao();

    /**
     * 获取show表单操作类
     * @return
     */
    public abstract ShowDao getShowDao();

    /**
     * 获取电视剧表单操作类
     * @return
     */
    public abstract TelevisionDao getTelevisionDao();

    /**
     * 获取用户表单操作类
     * @return
     */
    public abstract DouYinUserDao getDouYinUserDao();
}
