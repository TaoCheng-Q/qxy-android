package com.qxy.demo.entity;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoList extends BaseObservable {
    private List<Video> videoList = new ArrayList<>();
    private boolean has_more;
    private int cursor;

    public VideoList(){

    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
        notifyChange();
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
        notifyChange();
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
        notifyChange();
    }
}
