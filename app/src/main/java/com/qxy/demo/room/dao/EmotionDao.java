package com.qxy.demo.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.qxy.demo.room.entity.Emotion;

import java.util.List;

/**
 * 微博表情获取数据库操作类
 */
@SuppressWarnings("ALL")
@Dao
public interface EmotionDao {
    @Insert
    void insertEmotions(Emotion... emotion);

    @Query("SELECT * FROM Emotion ORDER BY id DESC")
    List<Emotion> queryEmotions();

    @Query("DELETE FROM Emotion")
    void deleteEmotions();
}
