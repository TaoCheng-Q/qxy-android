package com.qxy.demo.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.demo.entity.topList.MovieList;
import com.qxy.demo.entity.topList.ShowList;
import com.qxy.demo.entity.topList.TvList;
import com.qxy.demo.entity.topList.VersionList;
import com.qxy.demo.models.RequestDouYin;
import com.qxy.demo.room.entity.topListEntity.Movie;
import com.qxy.demo.room.entity.topListEntity.Show;
import com.qxy.demo.room.entity.topListEntity.Television;
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

public class TopListViewModel extends ViewModel {

    private MutableLiveData<MovieList> movieListMutableLiveData;

    private MutableLiveData<TvList> tvListMutableLiveData;

    private MutableLiveData<ShowList> showListMutableLiveData;

    private MutableLiveData<VersionList> versionListMutableLiveData;

    private int topListType = 2;

    private Callback clientCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                String token = res.getString("access_token");
                if(token!=null && !token.equals("")){
                    UserImformations.getInstance().setClient_token(token);
                    switch (topListType){
                        case 1:
                            getMovieListMutableLiveData(-1);
                            break;
                        case 2:
                            getTvListMutableLiveData(-1);
                            break;
                        case 3:
                            getShowListMutableLiveData(-1);
                            break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    public MutableLiveData<VersionList> getVersionListMutableLiveData(int type,int cursor,int page_count){
        if(versionListMutableLiveData==null){
            versionListMutableLiveData=new MutableLiveData<>();
        }

        RequestDouYin.getTopList(RequestDouYin.TOP_LIST_VERSION, getHeaderMap(), getVersionBodyMap(type, cursor, page_count), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    VersionList versionList = new VersionList();
                    List<Integer> integers = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                    if(res.getInt("error_code")==0){
                        versionList.setCursor(res.getInt("cursor"));
                        versionList.setHasMore(res.getBoolean("has_more"));
                        JSONArray array = new JSONArray(res.getString("list"));
                        for(int i=0;i<array.length();i++){
                            JSONObject item = (JSONObject) array.get(i);
                            integers.add(item.getInt("version"));
                        }
                        versionList.setIntegerList(integers);
                        versionListMutableLiveData.postValue(versionList);
                    }else {
//                        topListType=4;
//                        RequestDouYin.getClientToken(clientCallback);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return versionListMutableLiveData;
    }

    private Map<String,String> getVersionBodyMap(int type, int cursor, int page_count) {
        Map<String,String> bodyMap = new HashMap<>();
        bodyMap.put("type",String.valueOf(type));
        bodyMap.put("cursor",String.valueOf(cursor));
        bodyMap.put("count",String.valueOf(page_count));

        return bodyMap;
    }

    public MutableLiveData<MovieList> getMovieListMutableLiveData(int version){

        if(movieListMutableLiveData==null){
            movieListMutableLiveData=new MutableLiveData<>();
        }
//        网络获取电影排行榜列表
//        Log.d("TAG", "onFailure: xxxxxxxxxxxxx");
        
        RequestDouYin.getTopList(RequestDouYin.TOP_LIST,getHeaderMap(), getBodyMap("1",version), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: xxxxxxxxxxxxx");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    MovieList movieList = new MovieList();
                    List<Movie> movies = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
//                    Log.d("TAG", "onResponse: "+res.toString());
                    if(res.getInt("error_code")==0){
                        movieList.setActive_time(res.getString("active_time"));
                        JSONArray array = new JSONArray(res.getString("list"));
                        for(int i=0 ;i<array.length();i++){
                            JSONObject item = (JSONObject) array.get(i);
                            Movie movie = new Movie();
                            try {
                                movie.setActors(item.getString("actors"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
//                            Log.d("TAG", "onResponse: "+item.getString("actors"));
                            try {
                                movie.setAreas(item.getString("areas"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            try {
                                movie.setDirectors(item.getString("directors"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            movie.setDiscussion_hot(item.getString("discussion_hot"));
                            movie.setHot(item.getString("hot"));
                            movie.setInfluence_hot(item.getString("influence_hot"));
                            movie.setName(item.getString("name"));
                            movie.setName_en(item.getString("name_en"));
                            movie.setPoster(item.getString("poster"));
                            movie.setRelease_date(item.getString("release_date"));
                            movie.setSearch_hot(item.getString("search_hot"));
                            movie.setTopic_hot(item.getString("topic_hot"));
                            movies.add(movie);
                        }
                        movieList.setMovieList(movies);
                        Log.d("TopListViewModel", "onResponse: "+movieList.getMovieList().size());
                        movieListMutableLiveData.postValue(movieList);
                    }else {
                        //                ClientToken失效，需要重新获取
                        topListType=1;
                        RequestDouYin.getClientToken(clientCallback);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return movieListMutableLiveData;
    }

    private Map<String, String> getBodyMap(String type, int version) {
        Map<String,String> bodyMap = new HashMap<>();
        bodyMap.put("type",type);
        if (version != -1) {
            bodyMap.put("version", String.valueOf(version));
        }
        return bodyMap;
    }

    private Map<String, String> getHeaderMap() {
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("Content-Type","application/json");
        headerMap.put("access-token", UserImformations.getInstance().getClient_token());
        return headerMap;
    }

    public MutableLiveData<TvList> getTvListMutableLiveData(int version){
        if(tvListMutableLiveData==null){
            tvListMutableLiveData=new MutableLiveData<>();
        }
//        获取电视剧排行榜

        RequestDouYin.getTopList(RequestDouYin.TOP_LIST,getHeaderMap(), getBodyMap("2",version), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    TvList tvListEntity = new TvList();
                    List<Television> televisionList = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                    if(res.getInt("error_code")==0){
                        tvListEntity.setActive_time(res.getString("active_time"));
                        JSONArray array = new JSONArray(res.getString("list"));
                        for(int i=0 ;i<array.length();i++){
                            JSONObject item = (JSONObject) array.get(i);
                            Television television = new Television();
                            television.setActors(item.getString("actors"));
//                            television.setAreas(item.getString("areas"));
                            television.setDirectors(item.getString("directors"));
                            television.setDiscussion_hot(item.getString("discussion_hot"));
                            television.setHot(item.getString("hot"));
                            television.setInfluence_hot(item.getString("influence_hot"));
                            television.setName(item.getString("name"));
                            television.setName_en(item.getString("name_en"));
                            television.setPoster(item.getString("poster"));
                            television.setRelease_date(item.getString("release_date"));
                            television.setSearch_hot(item.getString("search_hot"));
                            television.setTopic_hot(item.getString("topic_hot"));
                            televisionList.add(television);
                        }
                        tvListEntity.setTelevisionList(televisionList);
                        tvListMutableLiveData.postValue(tvListEntity);
                    }else {
                        //                ClientToken失效，需要重新获取
                        topListType=2;
                        RequestDouYin.getClientToken(clientCallback);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return tvListMutableLiveData;
    }

    public MutableLiveData<ShowList> getShowListMutableLiveData(int version){
        if (showListMutableLiveData==null){
            showListMutableLiveData=new MutableLiveData<>();
        }
//        获取show榜单
        RequestDouYin.getTopList(RequestDouYin.TOP_LIST,getHeaderMap(), getBodyMap("3",version), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ShowList showListEntity = new ShowList();
                    List<Show> showList = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                    if(res.getInt("error_code")==0){
                        showListEntity.setActive_time(res.getString("active_time"));
                        JSONArray array = new JSONArray(res.getString("list"));
                        for(int i=0 ;i<array.length();i++){
                            JSONObject item =(JSONObject) array.get(i);
                            Show show = new Show();
//                            show.setActors(item.getString("actors"));
//                            show.setAreas(item.getString("areas"));
                            show.setDirectors(item.getString("directors"));
                            show.setDiscussion_hot(item.getString("discussion_hot"));
                            show.setHot(item.getString("hot"));
                            show.setInfluence_hot(item.getString("influence_hot"));
                            show.setName(item.getString("name"));
                            show.setName_en(item.getString("name_en"));
                            show.setPoster(item.getString("poster"));
                            show.setRelease_date(item.getString("release_date"));
                            show.setSearch_hot(item.getString("search_hot"));
                            show.setTopic_hot(item.getString("topic_hot"));
                            showList.add(show);
                        }
                        showListEntity.setShowList(showList);
                        showListMutableLiveData.postValue(showListEntity);
                    }else {
                        //                ClientToken失效，需要重新获取
                        topListType=3;
                        RequestDouYin.getClientToken(clientCallback);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return showListMutableLiveData;
    }
}
