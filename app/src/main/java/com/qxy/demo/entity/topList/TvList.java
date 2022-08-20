package com.qxy.demo.entity.topList;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.topListEntity.Television;

import java.util.List;

public class TvList extends BaseObservable {

    private List<Television> televisionList;

    public String active_time;

    public TvList(List<Television> televisionList) {
        this.televisionList = televisionList;
    }

    public TvList() {

    }

    public List<Television> getTelevisionList() {
        return televisionList;
    }

    public void setTelevisionList(List<Television> televisionList) {
        this.televisionList = televisionList;
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
