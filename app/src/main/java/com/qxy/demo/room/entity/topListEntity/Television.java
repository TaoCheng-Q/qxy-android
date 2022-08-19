package com.qxy.demo.room.entity.topListEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Television {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "name_en")
    private String name_en;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "release_date")
    private String release_date;

    @ColumnInfo(name = "actors")
    private String actors;

    @ColumnInfo(name = "directors")
    private String directors;

    @ColumnInfo(name = "discussion_hot")
    private long discussion_hot;

    @ColumnInfo(name = "hot")
    private long hot;

    @ColumnInfo(name = "influence_hot")
    private long influence_hot;

    @ColumnInfo(name = "search_hot")
    private long search_hot;

    @ColumnInfo(name = "topic_hot")
    private long topic_hot;

    public Television() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public long getDiscussion_hot() {
        return discussion_hot;
    }

    public void setDiscussion_hot(long discussion_hot) {
        this.discussion_hot = discussion_hot;
    }

    public long getHot() {
        return hot;
    }

    public void setHot(long hot) {
        this.hot = hot;
    }

    public long getInfluence_hot() {
        return influence_hot;
    }

    public void setInfluence_hot(long influence_hot) {
        this.influence_hot = influence_hot;
    }

    public long getSearch_hot() {
        return search_hot;
    }

    public void setSearch_hot(long search_hot) {
        this.search_hot = search_hot;
    }

    public long getTopic_hot() {
        return topic_hot;
    }

    public void setTopic_hot(long topic_hot) {
        this.topic_hot = topic_hot;
    }
}
