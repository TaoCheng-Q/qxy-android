package com.qxy.demo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.demo.entity.FansList;
import com.qxy.demo.models.RequestDouYin;
import com.qxy.demo.room.entity.Fans;
import com.qxy.demo.utils.CallBackUtil;
import com.qxy.demo.utils.OkhttpUtil;
import com.qxy.demo.utils.UserImformations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class FanViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<FansList> fansListMutableLiveData,followsListMutableLiveData;

//    获取粉丝列表
    public MutableLiveData<FansList> getFansListMutableLiveData(int cursor){
        if(fansListMutableLiveData==null){
            fansListMutableLiveData=new MutableLiveData<>();
        }
        OkhttpUtil.okHttpGet(RequestDouYin.FANS_LIST, getBodyMap(cursor), getHeader(), new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                paserJsonString(response);
            }
        });

        return fansListMutableLiveData;
    }

    private Map<String, String> getHeader() {
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("Content-Type","application/json");
        headerMap.put("access-token", UserImformations.getInstance().getClient_token());
        return headerMap;
    }

    private Map<String, String> getBodyMap(int cursor) {
        Map<String,String> bodyMap=new HashMap<>();
        bodyMap.put("open_id",UserImformations.getInstance().getOpen_id());
        bodyMap.put("cursor",String.valueOf(cursor));
        bodyMap.put("count",String.valueOf(RequestDouYin.PAGE_COUNT));
        return bodyMap;
    }

//    获取关注列表
    public MutableLiveData<FansList> getFollowsListMutableLiveData(int cursor){
        if(followsListMutableLiveData==null){
            followsListMutableLiveData=new MutableLiveData<>();
        }

        OkhttpUtil.okHttpGet(RequestDouYin.FOLLOWING_LIST, getBodyMap(cursor), getHeader(), new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                paserJsonString(response);
//                try {
//                    FansList fansListEntity=new FansList();
//                    List<Fans> fansList =new ArrayList<>();
//                    JSONObject res = new JSONObject(new JSONObject(response).getString("data"));
//                    if(res.getInt("error_code")==0){
//                        fansListEntity.setCursor(res.getInt("cursor"));
//                        fansListEntity.setHas_more(res.getBoolean("has_more"));
//                        JSONArray array = new JSONArray(res.getString("list"));
//                        for (int i=0;i<array.length();i++){
//                            Fans fans=new Fans();
//                            JSONObject item = (JSONObject) array.get(i);
//                            fans.setNickname(item.getString("nickname"));
//                            fans.setAvatar(item.getString("avatar"));
//                            fans.setCity(item.getString("city"));
//                            fans.setProvince(item.getString("province"));
//                            fans.setCountry(item.getString("country"));
//                            fans.setGender(item.getInt("gender"));
//                            fansList.add(fans);
//                        }
//                        fansListEntity.setFansList(fansList);
//                        followsListMutableLiveData.setValue(fansListEntity);
//                    }else {
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });

        return followsListMutableLiveData;
    }

    private void paserJsonString(String response) {
        try {
            FansList fansListEntity=new FansList();
            List<Fans> fansList =new ArrayList<>();
            JSONObject res = new JSONObject(new JSONObject(response).getString("data"));
            if(res.getInt("error_code")==0){
                fansListEntity.setCursor(res.getInt("cursor"));
                fansListEntity.setHas_more(res.getBoolean("has_more"));
                JSONArray array = new JSONArray(res.getString("list"));
                for (int i=0;i<array.length();i++){
                    Fans fans=new Fans();
                    JSONObject item = (JSONObject) array.get(i);
                    fans.setNickname(item.getString("nickname"));
                    fans.setAvatar(item.getString("avatar"));
                    fans.setCity(item.getString("city"));
                    fans.setProvince(item.getString("province"));
                    fans.setCountry(item.getString("country"));
                    fans.setGender(item.getInt("gender"));
                    fansList.add(fans);
                }
                fansListEntity.setFansList(fansList);
                followsListMutableLiveData.setValue(fansListEntity);
            }else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}