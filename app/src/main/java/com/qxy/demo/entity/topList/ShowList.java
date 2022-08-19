package com.qxy.demo.entity.topList;

import androidx.databinding.BaseObservable;

import com.qxy.demo.room.entity.topListEntity.Show;

import java.util.List;

public class ShowList extends BaseObservable {

    private List<Show> showList;

    public ShowList(List<Show> showList) {
        this.showList = showList;
    }

    public List<Show> getShowList() {
        return showList;
    }

    public void setShowList(List<Show> showList) {
        this.showList = showList;
        notifyChange();
    }
}
