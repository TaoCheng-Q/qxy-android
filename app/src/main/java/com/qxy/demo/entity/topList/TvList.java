package com.qxy.demo.entity.topList;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.topListEntity.Television;

import java.util.List;

public class TvList extends BaseObservable {

    private List<Television> televisionList;

    public TvList(List<Television> televisionList) {
        this.televisionList = televisionList;
    }

    public List<Television> getTelevisionList() {
        return televisionList;
    }

    public void setTelevisionList(List<Television> televisionList) {
        this.televisionList = televisionList;
        notifyChange();
    }
}
