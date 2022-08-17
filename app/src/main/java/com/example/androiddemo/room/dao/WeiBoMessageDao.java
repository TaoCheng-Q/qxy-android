package com.example.androiddemo.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androiddemo.room.entity.WeiBoMessage;

import java.util.List;

@SuppressWarnings("ALL")
@Dao
public interface WeiBoMessageDao {
    /**
     * 数据库插入微博极简短消息
     * @param weiBoMessage
     */

    @Insert
    void insertWeiBoMessages(WeiBoMessage... weiBoMessage);

    /**
     * 查询数据所有微博极简短消息
     * @return
     */
    @Query("SELECT * FROM WeiBoMessage")
    List<WeiBoMessage> queryWeiBoMessages();

    /**
     * 删除微博极简短消息
     */
    @Query("DELETE FROM WeiBoMessage")
    void deleteWeiBoMessages();
}
