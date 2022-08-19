package com.qxy.demo.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Fans {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nickname")
    private String nickname;

    @ColumnInfo(name = "avatar")
    private String avatar;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "province")
    private String province;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "gender")
    private int gender;

    public Fans() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
