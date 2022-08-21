package com.qxy.demo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.demo.entity.WeiBoMessageList;
import com.qxy.demo.room.entity.WeiBoMessage;
import com.qxy.demo.utils.CallBackUtil;
import com.qxy.demo.utils.OkhttpUtil;
import com.qxy.demo.utils.RoomUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class WeiBoMessagesViewModel extends ViewModel {

    public int PAGE_COUNT = 10;
    String WEIBO_EMOTIONS_URL="https://api.weibo.com/2/statuses/home_timeline.json";
    String WEIBO_ACCESS_TOKEN = "2.00gImvyHeoJoOEd591163c9aKmi6wB";


    private MutableLiveData<WeiBoMessageList> weiBoMessageMutableLiveData ;

    public MutableLiveData<WeiBoMessageList> getWeiBoMessageMutableLiveData(int page){
        if(weiBoMessageMutableLiveData==null){
            weiBoMessageMutableLiveData=new MutableLiveData<>();
        }

        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("access_token",WEIBO_ACCESS_TOKEN);
        int fromIndex = page*PAGE_COUNT;

        OkhttpUtil.okHttpGet(WEIBO_EMOTIONS_URL, stringMap, new CallBackUtil.CallBackString() {

            @Override
            public void onFailure(Call call, Exception e) {
//                获取本地数据
//                Room.databaseBuilder(content, MyDataBase.class,"MyDataBase").build();
                if(page==0){
                    getMessagesByRoom(fromIndex);
                }

            }

            @Override
            public void onResponse(String response) {
                try {
                    List<WeiBoMessage> weiBoMessageList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = new JSONArray(jsonObject.getString("statuses"));
                    for(int i = fromIndex;i<Math.min(fromIndex+PAGE_COUNT ,array.length());i++){
                        JSONObject item = (JSONObject) array.get(i);
                        WeiBoMessage weiBoMessage = new WeiBoMessage();
                        weiBoMessage.setCreated_at(item.getString("created_at"));
                        weiBoMessage.setCan_edit(item.getBoolean("can_edit"));
                        try {
                            weiBoMessage.setVersion(item.getInt("version"));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        weiBoMessage.setText(item.getString("text"));
                        weiBoMessage.setTextLength(item.getInt("textLength"));
                        weiBoMessage.setSource_allowclick(item.getInt("source_allowclick"));
                        weiBoMessage.setSource_type(item.getInt("source_type"));
                        weiBoMessage.setSource(item.getString("source"));
                        weiBoMessageList.add(weiBoMessage);
                    }
                    if(!weiBoMessageList.isEmpty()){
                        WeiBoMessageList messageList = new WeiBoMessageList(weiBoMessageList);
//                        emotions.setWeiboEmotionsList(emotionsList);
                        weiBoMessageMutableLiveData.postValue(messageList);
                    }else{
                        if(page==0){
                            getMessagesByRoom(fromIndex);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    getMessagesByRoom(fromIndex);
                }
            }
        });

        return weiBoMessageMutableLiveData;
    }

    private void getMessagesByRoom(int fromIndex) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                List<WeiBoMessage> weiBoMessageList = new ArrayList<>();
                List<WeiBoMessage> weiBoMessages = RoomUtil.getInstance().getRoomInstance().getWeiBoMessageDao().queryWeiBoMessages();
//                for(int i=fromIndex;i<Math.min(fromIndex+PAGE_COUNT,weiBoMessages.size());i++){
//                    WeiBoMessage item = weiBoMessages.get(i);
//                    WeiboEmotions emotions = new WeiboEmotions();
//                    emotions.setType(item.getType());
//                    emotions.setHot(item.isHot());
//                    emotions.setCommon(item.isCommon());
//                    emotions.setIcon(item.getIcon());
//                    emotions.setValue(item.getValue());
//                    weiBoMessageList.add(emotions);
//                }
                if(weiBoMessages!=null && !weiBoMessages.isEmpty()){
                    WeiBoMessageList messageList = new WeiBoMessageList(weiBoMessages);
//                    emotions.setWeiboEmotionsList(emotionsList);
                    weiBoMessageMutableLiveData.postValue(messageList);
                }
            }
        });

        thread.start();

    }
}
