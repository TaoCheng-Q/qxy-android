package com.qxy.demo.entity;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.Fans;

import java.util.List;

public class FansList extends BaseObservable {
    private List<Fans> fansList;
    private int cursor;
    private boolean has_more;

    public FansList(){

    }

    public List<Fans> getFansList() {
        return fansList;
    }

    public void setFansList(List<Fans> fansList) {
        this.fansList = fansList;
        notifyChange();
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
        notifyChange();
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
        notifyChange();
    }
}
