package com.qxy.demo.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.qxy.demo.room.entity.DouYinUser;

import java.util.List;

/**
 * 用户表操作
 */
@Dao
public interface DouYinUserDao {
    /**
     * 用户数据插入
     * @param douYinUser
     */
    @Insert
    void insertDouYinUserDao(DouYinUser... douYinUser);

    /**
     * 查询所有用户操作
     * @return
     */
    @Query("SELECT * FROM DouYinUser")
    List<DouYinUser> queryDouYinUser();

    /**
     * 删除所有用户操作
     */
    @Query("DELETE FROM DouYinUser")
    void deleteDouYinUser();

}
