package com.qxy.demo.room.dao.topListDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.qxy.demo.room.entity.topListEntity.Show;
import com.qxy.demo.room.entity.topListEntity.Television;

import java.util.List;
@Dao
public interface ShowDao {
    /**
     * 插入电视剧实体
     * @param shows
     */
    @Insert
    void insertShow(Show... shows);

    /**
     * 查询电视剧实体
     * @return
     */
    @Query("SELECT * FROM Show")
    List<Show> queryShows();

    /**
     * 删除所有
     */
    @Query("DELETE FROM Show")
    void deleteShows();
}
