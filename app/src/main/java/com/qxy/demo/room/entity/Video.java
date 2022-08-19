package com.qxy.demo.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Video {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "create_time")
    private String create_time;

    @ColumnInfo(name = "is_reviewed")
    private boolean is_reviewed;

    @ColumnInfo(name = "video_status")
    private int video_status;

    @ColumnInfo(name = "share_url")
    private String share_url;

    @ColumnInfo(name = "item_id")
    private String item_id;

    @ColumnInfo(name = "media_type")
    private int media_type;

    @ColumnInfo(name = "cover")
    private String cover;

    @ColumnInfo(name = "forward_count")
    private int forward_count;

    @ColumnInfo(name = "comment_count")
    private int comment_count;

    @ColumnInfo(name = "digg_count")
    private int digg_count;

    @ColumnInfo(name = "download_count")
    private int download_count;

    @ColumnInfo(name = "play_count")
    private int play_count;

    @ColumnInfo(name = "share_count")
    private int share_count;

    public Video() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public boolean isIs_reviewed() {
        return is_reviewed;
    }

    public void setIs_reviewed(boolean is_reviewed) {
        this.is_reviewed = is_reviewed;
    }

    public int getVideo_status() {
        return video_status;
    }

    public void setVideo_status(int video_status) {
        this.video_status = video_status;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getForward_count() {
        return forward_count;
    }

    public void setForward_count(int forward_count) {
        this.forward_count = forward_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }
}
