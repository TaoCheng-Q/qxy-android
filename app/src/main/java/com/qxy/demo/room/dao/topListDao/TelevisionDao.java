package com.qxy.demo.room.dao.topListDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.qxy.demo.room.entity.topListEntity.Television;

import java.util.List;

/**
 * 电视榜单表操作
 */
@Dao
public interface TelevisionDao {
    /**
     * 插入电视剧实体
     * @param televisions
     */
    @Insert
    void insertTelevision(Television... televisions);

    /**
     * 查询电视剧实体
     * @return
     */
    @Query("SELECT * FROM Television")
    List<Television> queryTelevisions();

    /**
     * 删除所有
     */
    @Query("DELETE FROM Television")
    void deleteTelevisions();
}
