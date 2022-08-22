package com.qxy.demo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.demo.entity.VideoList;
import com.qxy.demo.entity.topList.ShowList;
import com.qxy.demo.models.RequestDouYin;
import com.qxy.demo.room.entity.DouYinUser;
import com.qxy.demo.room.entity.Video;
import com.qxy.demo.room.entity.topListEntity.Show;
import com.qxy.demo.utils.CallBackUtil;
import com.qxy.demo.utils.OkhttpUtil;
import com.qxy.demo.utils.RoomUtil;
import com.qxy.demo.utils.UserImformations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserInfoViewModel extends ViewModel {
//    记录是否第一次请求用户公开信息，判断是否需要从数据库中获取数据
    private boolean firstTag=true;

//    用户公开信息存放
    private MutableLiveData<DouYinUser> douYinUserMutableLiveData;

//    视频列表信息存放
    private MutableLiveData<VideoList> videoListMutableLiveData;
//    获取accessToken的回调
    private Callback accessTokenCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
                try {
//                Log.d("TAG", "onResponse: "+response.body().string());
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject json = new JSONObject(jsonObject.getString("data"));
                    if(json.getInt("error_code")==0){
//                        成功获取到数据
                        UserImformations.getInstance().setToken(json.getString("access_token"));
                        try {
                            UserImformations.getInstance().setOpen_id(json.getString("open_id"));
                        }catch (Exception e){

                        }
                        try{
                            UserImformations.getInstance().setRefresh_token(json.getString("refresh_token"));
                        }catch (Exception e){

                        }

                        getDouYinUserMutableLiveData();
                    }else {

                    }
//                    Log.d("TAG", "onResp: "+"token"+UserImformations.getInstance().getToken()+"//open-id"+UserImformations.getInstance().getOpen_id()+"//"+UserImformations.getInstance().getCode());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    };

//    用户视频列表信息请求
    public MutableLiveData<VideoList> getVideoListMutableLiveData(int cursor){
        if(videoListMutableLiveData==null){
            videoListMutableLiveData=new MutableLiveData<>();
        }
        Map<String,String> paramMap=new HashMap<>();
        paramMap.put("open_id",UserImformations.getInstance().getOpen_id());
        paramMap.put("cursor",String.valueOf(cursor));
        paramMap.put("count",String.valueOf(RequestDouYin.PAGE_COUNT));
        OkhttpUtil.okHttpGet(RequestDouYin.VIDEO_LIST, paramMap, getHeaderMap(), new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                try {
                    VideoList videoListEntity = new VideoList();
                    List<Video> videoList = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response).getString("data"));
                    if(res.getInt("error_code")==0){
//                            成功获取到视频列表信息
                        videoListEntity.setHas_more(res.getBoolean("has_more"));
                        videoListEntity.setCursor(res.getInt("cursor"));
                        JSONArray array = new JSONArray(res.getString("list"));
                        for(int i=0;i<array.length();i++){

                            Video video = new Video();
                            JSONObject item  = (JSONObject) array.get(i);
                            video.setTitle(item.getString("title"));
                            video.setCreate_time(item.getString("create_time"));
                            video.setIs_top(item.getBoolean("is_top"));
                            video.setShare_url(item.getString("share_url"));
                            video.setItem_id(item.getString("item_id"));
                            video.setCover(item.getString("cover"));
                            JSONObject it = new JSONObject(item.getString("statistics"));
                            video.setForward_count(it.getInt("forward_count"));
                            video.setComment_count(it.getInt("comment_count"));
                            video.setDigg_count(it.getInt("digg_count"));
                            video.setDownload_count(it.getInt("download_count"));
                            video.setPlay_count(it.getInt("play_count"));
                            video.setShare_count(it.getInt("share_count"));
                            video.setMedia_type(it.getInt("media_type"));
                            videoList.add(video);
                        }
                        if(!videoList.isEmpty()){
                            videoListEntity.setVideoList(videoList);
                            videoListMutableLiveData.postValue(videoListEntity);
                        }
                    }else {
//                        accessToken可能过期
//                        RequestDouYin.getClientToken(accessTokenCallback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return videoListMutableLiveData;
    }

//    用户公开信息请求
    public MutableLiveData<DouYinUser> getDouYinUserMutableLiveData(){
        if(douYinUserMutableLiveData==null){
            douYinUserMutableLiveData=new MutableLiveData<>();
        }

        String bodyJson="{\"access_token\":\""+UserImformations.getInstance().getToken()+"\",\"open_id\":\""+UserImformations.getInstance().getOpen_id()+"\"}";
        OkhttpUtil.okHttpPostJson(RequestDouYin.USERINFO, bodyJson, getHeaderMap(), new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                if(firstTag){
//                    第一次请求失败后获取数据库数据
                    firstTag=false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<DouYinUser> user = RoomUtil.getInstance().getRoomInstance().getDouYinUserDao().queryDouYinUser();
                            if(!user.isEmpty()){
                                douYinUserMutableLiveData.postValue(user.get(0));
                            }
                        }
                    }).start();

                }

            }

            @Override
            public void onResponse(String response) {
                try {
                    DouYinUser douYinUser = new DouYinUser();
                    JSONObject res = new JSONObject(new JSONObject(response).getString("data"));
                    if(res.getInt("error_code")==0){
//                        成功获取到用户信息
                        douYinUser.setAvatar(res.getString("avatar"));
                        douYinUser.setCity(res.getString("city"));
                        douYinUser.setCountry(res.getString("country"));
                        douYinUser.setDescription(res.getString("description"));
                        douYinUser.setGender(res.getInt("gender"));
                        douYinUser.setNickname(res.getString("nickname"));
                        douYinUser.setProvince(res.getString("province"));
                        douYinUserMutableLiveData.postValue(douYinUser);
                    }else {
                        if(firstTag){
//                    第一次请求失败后获取数据库数据
                            firstTag=false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<DouYinUser> user = RoomUtil.getInstance().getRoomInstance().getDouYinUserDao().queryDouYinUser();
                                    if(!user.isEmpty()){
                                        douYinUserMutableLiveData.postValue(user.get(0));
                                    }
                                }
                            }).start();

                        }
                        RequestDouYin.getClientToken(accessTokenCallback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return douYinUserMutableLiveData;
    }

//    请求头参数
    private Map<String, String> getHeaderMap() {
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("access-token", UserImformations.getInstance().getToken());
        return header;
    }
}
