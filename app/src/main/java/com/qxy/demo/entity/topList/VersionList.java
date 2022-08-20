package com.qxy.demo.entity.topList;

import androidx.databinding.BaseObservable;

import java.util.List;

public class VersionList extends BaseObservable {

    private List<Integer> integerList;

    private int cursor;

    private boolean hasMore;




    public VersionList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public VersionList() {

    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
        notifyChange();
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
        notifyChange();
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        notifyChange();
    }
}
