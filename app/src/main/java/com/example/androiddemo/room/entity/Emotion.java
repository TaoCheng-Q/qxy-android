package com.example.androiddemo.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Emotion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(defaultValue = "type")
    private String type;

    @ColumnInfo(defaultValue = "hot")
    private boolean hot;

    @ColumnInfo(defaultValue = "common")
    private boolean common;

    @ColumnInfo(defaultValue = "value")
    private String value;

    @ColumnInfo(defaultValue = "icon")
    private String icon;

    @Ignore
    public Emotion(int id,String type, boolean hot, boolean common, String value, String icon) {
        this.id = id;
        this.type = type;
        this.hot = hot;
        this.common = common;
        this.value = value;
        this.icon = icon;
    }


    public Emotion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
