package com.qxy.demo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.demo.entity.VideoList;
import com.qxy.demo.models.RequestDouYin;
import com.qxy.demo.room.entity.Video;
import com.qxy.demo.utils.OkhttpUtil;
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

public class ArtDetailViewModel extends ViewModel {
//    存放视频列表数据
    private MutableLiveData<VideoList> videoListMutableLiveData;
    //    记录第一次请求，判断是否需要数据库的数据请求
    private boolean firstTag = true;

//    获取视频
    public MutableLiveData<VideoList> getVideoListMutableLiveData(ArrayList<String> itemIdList){
        if(videoListMutableLiveData==null){
            videoListMutableLiveData=new MutableLiveData<>();
        }
//        OkhttpUtil.okHttpPost(RequestDouYin.VIDEO_DATA,get);
        RequestDouYin.post(RequestDouYin.VIDEO_DATA, getBodyMap(itemIdList), getParamMap(), getHeaderMap(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    VideoList videoListEntity = new VideoList();
                    List<Video> videoList = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                    if (res.getInt("error_code")==0){
//                        数据获取成功
                        JSONArray array=new JSONArray(res.getString("list"));
                        for(int i=0;i<array.length();i++){
                            JSONObject item = (JSONObject) array.get(i);
                            Video video=new Video();
                            video.setTitle(item.getString("title"));
                            video.setCreate_time(item.getString("create_time"));
                            video.setShare_url("share_url");
                            video.setCover(item.getString("cover"));
                            JSONObject jsonObject = new JSONObject(item.getString("statistics"));
                            video.setDigg_count(jsonObject.getInt("digg_count"));
                            video.setDownload_count(jsonObject.getInt("download_count"));
                            video.setPlay_count(jsonObject.getInt("play_count"));
                            video.setShare_count(jsonObject.getInt("share_count"));
                            video.setForward_count(jsonObject.getInt("forward_count"));
                            video.setComment_count(jsonObject.getInt("comment_count"));
                            videoList.add(video);
                        }
                        if(!videoList.isEmpty()){
                            videoListEntity.setVideoList(videoList);
                            videoListMutableLiveData.postValue(videoListEntity);
                        }
                    }else {
//                        accessToken可能已经过期重新获取
                        RequestDouYin.requestAccessToken(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    JSONObject r = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                                    if(r.getInt("error_code")==0){
//                                        获取数据成功后存储在单例中，并且再次请求视频数据
                                        UserImformations.getInstance().setToken(r.getString("access_token"));
                                        UserImformations.getInstance().setOpen_id(r.getString("open_id"));
                                        RequestDouYin.post(RequestDouYin.VIDEO_DATA,
                                                getBodyMap(itemIdList),
                                                getParamMap(),
                                                getHeaderMap(),
                                                this);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return videoListMutableLiveData;
    }

    private Map<String, String> getBodyMap(ArrayList<String> itemIdList) {
        Map<String,String> bodyMap=new HashMap<>();
        bodyMap.put("item_ids", (new JSONArray(itemIdList)).toString());
        return bodyMap;
    }

    private Map<String, String> getParamMap() {
        Map<String,String> paramMap =new HashMap<>();
        paramMap.put("open_id",UserImformations.getInstance().getOpen_id());
        return paramMap;
    }

    private Map<String, String> getHeaderMap() {
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        headerMap.put("access-token", UserImformations.getInstance().getToken());
        return headerMap;
    }
}
