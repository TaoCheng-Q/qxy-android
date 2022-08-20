package com.qxy.demo.entity.topList;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.topListEntity.Show;

import java.util.List;

public class ShowList extends BaseObservable {

    private List<Show> showList;

    private String active_time;

    public ShowList(List<Show> showList) {
        this.showList = showList;
    }

    public ShowList() {

    }

    public List<Show> getShowList() {
        return showList;
    }

    public void setShowList(List<Show> showList) {
        this.showList = showList;
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
