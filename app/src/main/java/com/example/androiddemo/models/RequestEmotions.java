package com.example.androiddemo.models;

import androidx.lifecycle.LiveData;

import com.example.androiddemo.entity.WeiboEmotions;
import com.example.androiddemo.utils.CallBackUtil;
import com.example.androiddemo.utils.OkhttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class RequestEmotions {

    int PAGE_COUNT = 10;
    String WEIBO_EMOTIONS_URL="https://api.weibo.com/2/emotions.json";
    String WEIBO_ACCESS_TOKEN = "2.00gImvyHeoJoOEd591163c9aKmi6wB";

    public RequestEmotions(){

    }

    public List<WeiboEmotions> getWeiboEmotions(int page){
        List<WeiboEmotions> emotionsList = new ArrayList<>();
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("access_token",WEIBO_ACCESS_TOKEN);
        int fromIndex = page*PAGE_COUNT;

        OkhttpUtil.okHttpGet(WEIBO_EMOTIONS_URL, stringMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
//                获取本地数据
            }

            @Override
            public void onResponse(String response) {
                try {
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        if(emotionsList.isEmpty()){

        }

        return emotionsList;
    }
}
