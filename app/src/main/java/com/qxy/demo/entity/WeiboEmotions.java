package com.qxy.demo.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

public class WeiboEmotions extends BaseObservable {
    private String phrase;
    private String type;
    private String url;
    private boolean hot;
    private boolean common;
    private String category;
    private String value;
    private String icon;


    public List<WeiboEmotions> weiboEmotionsList = new ArrayList<>();

    public WeiboEmotions(String phrase, String type, String url, boolean hot, boolean common, String category, String value, String icon) {
        this.phrase = phrase;
        this.type = type;
        this.url = url;
        this.hot = hot;
        this.common = common;
        this.category = category;
        this.value = value;
        this.icon = icon;
    }

    public WeiboEmotions() {
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Bindable
    public List<WeiboEmotions> getWeiboEmotionsList() {
        return weiboEmotionsList;
    }

    public void setWeiboEmotionsList(List<WeiboEmotions> weiboEmotionsList) {
        this.weiboEmotionsList = weiboEmotionsList;
        notifyChange();
    }
}
