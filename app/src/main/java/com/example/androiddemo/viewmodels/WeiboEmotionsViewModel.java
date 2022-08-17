package com.example.androiddemo.viewmodels;

import android.os.Handler;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.androiddemo.FragmentView.MainPage2Fragment;
import com.example.androiddemo.entity.WeiboEmotions;
import com.example.androiddemo.models.RequestEmotions;
import com.example.androiddemo.room.DataBases.MyDataBase;
import com.example.androiddemo.room.entity.Emotion;
import com.example.androiddemo.utils.CallBackUtil;
import com.example.androiddemo.utils.OkhttpUtil;
import com.example.androiddemo.utils.RoomUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;


public class WeiboEmotionsViewModel extends ViewModel {

    public int PAGE_COUNT = 10;
    String WEIBO_EMOTIONS_URL="https://api.weibo.com/2/emotions.json";
    String WEIBO_ACCESS_TOKEN = "2.00gImvyHeoJoOEd591163c9aKmi6wB";


    private MutableLiveData<WeiboEmotions> emotionsLiveData ;

    public LiveData<WeiboEmotions> getEmotions(int page){
        if(emotionsLiveData==null){
            emotionsLiveData=new MutableLiveData<>();
        }

        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("access_token",WEIBO_ACCESS_TOKEN);
        int fromIndex = page*PAGE_COUNT;

        OkhttpUtil.okHttpGet(WEIBO_EMOTIONS_URL, stringMap, new CallBackUtil.CallBackString() {

            @Override
            public void onFailure(Call call, Exception e) {
//                获取本地数据
//                Room.databaseBuilder(content, MyDataBase.class,"MyDataBase").build();
                getEmotionsByRoom(fromIndex);
            }

            @Override
            public void onResponse(String response) {
                try {
                    List<WeiboEmotions> emotionsList = new ArrayList<>();
                    JSONArray array = new JSONArray(response);
                    for(int i = fromIndex;i<Math.min(fromIndex+PAGE_COUNT ,array.length());i++){
                        JSONObject item = (JSONObject) array.get(i);
                        WeiboEmotions emotions = new WeiboEmotions();
                        emotions.setType(item.getString("type"));
                        emotions.setHot(item.getBoolean("hot"));
                        emotions.setCommon(item.getBoolean("common"));
                        emotions.setIcon(item.getString("icon"));
                        emotions.setValue(item.getString("value"));
                        emotionsList.add(emotions);
                    }
                    if(!emotionsList.isEmpty()){
                        WeiboEmotions emotions = new WeiboEmotions();
                        emotions.setWeiboEmotionsList(emotionsList);
                        emotionsLiveData.postValue(emotions);
                    }else{
                        getEmotionsByRoom(fromIndex);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    getEmotionsByRoom(fromIndex);
                }
            }
        });

//        new RequestEmotions().getWeiboEmotions(page);
//        List<WeiboEmotions> list = new RequestEmotions().getWeiboEmotions(page);
//        if(list.isEmpty()){
//
//        }else{
//            Objects.requireNonNull(emotionsLiveData.getValue()).setWeiboEmotionsList(list);
//        }

        return emotionsLiveData;
    }

    private void getEmotionsByRoom(int fromIndex) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<WeiboEmotions> emotionsList = new ArrayList<>();
                List<Emotion> emotionsLive = RoomUtil.getInstance().getRoomInstance().getEmotionDao().queryEmotions();
                for(int i=fromIndex;i<Math.min(fromIndex+PAGE_COUNT,emotionsLive.size());i++){
                    Emotion item = emotionsLive.get(i);
                    WeiboEmotions emotions = new WeiboEmotions();
                    emotions.setType(item.getType());
                    emotions.setHot(item.isHot());
                    emotions.setCommon(item.isCommon());
                    emotions.setIcon(item.getIcon());
                    emotions.setValue(item.getValue());
                    emotionsList.add(emotions);
                }
                if(!emotionsList.isEmpty()){
                    WeiboEmotions emotions = new WeiboEmotions();
                    emotions.setWeiboEmotionsList(emotionsList);
                    emotionsLiveData.postValue(emotions);
                }
            }
        });

        thread.start();

    }
}
