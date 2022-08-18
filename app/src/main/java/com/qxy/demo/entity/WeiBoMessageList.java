package com.qxy.demo.entity;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.WeiBoMessage;

import java.util.ArrayList;
import java.util.List;

public class WeiBoMessageList extends BaseObservable {

    private List<WeiBoMessage> weiBoMessageList = new ArrayList<>();

    public WeiBoMessageList(List<WeiBoMessage> weiBoMessageList) {
        this.weiBoMessageList = weiBoMessageList;
        notifyChange();
    }

    public List<WeiBoMessage> getWeiBoMessageList() {
        return weiBoMessageList;
    }

    public void setWeiBoMessageList(List<WeiBoMessage> weiBoMessageList) {
        this.weiBoMessageList = weiBoMessageList;
        notifyChange();
    }
}
