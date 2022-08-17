package com.example.androiddemo.utils;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androiddemo.room.DataBases.MyDataBase;

public class RoomUtil {

    private static RoomUtil roomUtil = null;

    private MyDataBase roomDatabase;

    private  RoomUtil (){

    }

    public static RoomUtil getInstance(){
        if (roomUtil==null){
            roomUtil=new RoomUtil();
        }
        return roomUtil;
    }

    public void setRoomDatabase(Context context){
        roomDatabase = Room.databaseBuilder(context, MyDataBase.class,"MyDataBase").build();
    }

    public  MyDataBase getRoomInstance(){
        return roomDatabase;
    }

//    Room.databaseBuilder(content, MyDataBase.class,"MyDataBase").build();
}
