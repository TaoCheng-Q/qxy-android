package com.example.androiddemo.room.DataBases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androiddemo.room.dao.EmotionDao;
import com.example.androiddemo.room.dao.WeiBoMessageDao;
import com.example.androiddemo.room.entity.Emotion;
import com.example.androiddemo.room.entity.WeiBoMessage;

@Database(entities = {Emotion.class, WeiBoMessage.class},version = 1)
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
}
