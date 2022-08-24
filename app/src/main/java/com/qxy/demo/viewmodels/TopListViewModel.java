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

public class TopListViewModel extends ViewModel {

    //    记录第一次请求，判断是否需要数据库的数据请求
    private boolean firstMoviesTag = true;
    private boolean firstTvsTag = true;
    private boolean firstShowsTag = true;

//    电影榜单数据存放
    private MutableLiveData<MovieList> movieListMutableLiveData;

//    电视剧榜单数据存放
    private MutableLiveData<TvList> tvListMutableLiveData;

//    shows榜单数据存放
    private MutableLiveData<ShowList> showListMutableLiveData;

//    版本信息数据存放
    private MutableLiveData<VersionList> versionListMutableLiveData;

//    默认初始化为电视剧
    private int topListType = 2;

//    获取accesstoken请求回调
    private Callback clientCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                if(res.getInt("error_code")==0){
//                    获取ClientToken数据成功后，重新请求榜单数据
                    String token = res.getString("access_token");
                    if(!token.equals("")){
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

//    请求版本信息，所有的榜单通用
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
//                        榜单信息获取成功
                        versionList.setCursor(res.getInt("cursor"));
                        versionList.setHasMore(res.getBoolean("has_more"));
                        JSONArray array = new JSONArray(res.getString("list"));
                        for(int i=0;i<array.length();i++){
                            JSONObject item = (JSONObject) array.get(i);
                            integers.add(item.getInt("version"));
                        }
                        if(!integers.isEmpty()){
                            versionList.setIntegerList(integers);
                            versionListMutableLiveData.postValue(versionList);
                        }
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

//    电影榜单通过版本请求数据，版本为-1的时候默认考虑本周
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
                if(firstMoviesTag){
//                第一次请求失败后获取数据库数据
                    firstMoviesTag=false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Movie> movies = RoomUtil.getInstance().getRoomInstance().getMovieDao().queryMovies();
                            if(!movies.isEmpty()){
                                MovieList ml = new MovieList();
                                ml.setMovieList(movies);
                                movieListMutableLiveData.postValue(ml);
                            }
                        }
                    }).start();

                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    MovieList movieList = new MovieList();
                    List<Movie> movies = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
//                    Log.d("TAG", "onResponse: "+res.toString());
                    if(res.getInt("error_code")==0){
//                        电影榜单获取成功
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
                        if(!movies.isEmpty()){
                            firstMoviesTag=false;
                            movieList.setMovieList(movies);
                            Log.d("TopListViewModel", "onResponse: "+movieList.getMovieList().size());
                            movieListMutableLiveData.postValue(movieList);
                        }

                    }else {
                        if(firstMoviesTag){
//                    第一次请求失败后获取数据库数据
                            firstMoviesTag=false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Movie> movies = RoomUtil.getInstance().getRoomInstance().getMovieDao().queryMovies();
                                    if(!movies.isEmpty()){
                                        MovieList ml = new MovieList();
                                        ml.setMovieList(movies);
                                        movieListMutableLiveData.postValue(ml);
                                    }
                                }
                            }).start();

                        }
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

//    电视剧榜单请求
    public MutableLiveData<TvList> getTvListMutableLiveData(int version){
        if(tvListMutableLiveData==null){
            tvListMutableLiveData=new MutableLiveData<>();
        }
//        获取电视剧排行榜

        RequestDouYin.getTopList(RequestDouYin.TOP_LIST,getHeaderMap(), getBodyMap("2",version), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if(firstTvsTag){
//                    第一次请求失败后获取数据库数据
                    firstTvsTag=false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Television> tvs = RoomUtil.getInstance().getRoomInstance().getTelevisionDao().queryTelevisions();
                            if(!tvs.isEmpty()){
                                TvList tl = new TvList();
                                tl.setTelevisionList(tvs);
                                tvListMutableLiveData.postValue(tl);
                            }
                        }
                    }).start();

                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    TvList tvListEntity = new TvList();
                    List<Television> televisionList = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                    if(res.getInt("error_code")==0){
//                        电视剧榜单数据获取成功
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
                        if(!televisionList.isEmpty()){
                            firstTvsTag=false;
                            tvListEntity.setTelevisionList(televisionList);
                            tvListMutableLiveData.postValue(tvListEntity);
                        }

                    }else {
                        if(firstTvsTag){
//                    第一次请求失败后获取数据库数据
                            firstTvsTag=false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Television> tvs = RoomUtil.getInstance().getRoomInstance().getTelevisionDao().queryTelevisions();
                                    if(!tvs.isEmpty()){
                                        TvList tl = new TvList();
                                        tl.setTelevisionList(tvs);
                                        tvListMutableLiveData.postValue(tl);
                                    }
                                }
                            }).start();

                        }
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

//    shows榜单请求
    public MutableLiveData<ShowList> getShowListMutableLiveData(int version){
        if (showListMutableLiveData==null){
            showListMutableLiveData=new MutableLiveData<>();
        }
//        获取show榜单
        RequestDouYin.getTopList(RequestDouYin.TOP_LIST,getHeaderMap(), getBodyMap("3",version), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(firstShowsTag){
//                    第一次请求失败后获取数据库数据
                    firstShowsTag=false;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Show> shows = RoomUtil.getInstance().getRoomInstance().getShowDao().queryShows();
                            if(!shows.isEmpty()){
                                ShowList sl = new ShowList();
                                sl.setShowList(shows);
                                showListMutableLiveData.postValue(sl);
                            }
                        }
                    }).start();

                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ShowList showListEntity = new ShowList();
                    List<Show> showList = new ArrayList<>();
                    JSONObject res = new JSONObject(new JSONObject(response.body().string()).getString("data"));
                    if(res.getInt("error_code")==0){
//                        show榜单获取成功
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
                        if(!showList.isEmpty()){
                            firstShowsTag=false;
                            showListEntity.setShowList(showList);
                            showListMutableLiveData.postValue(showListEntity);
                        }

                    }else {
                        if(firstShowsTag){
//                    第一次请求失败后获取数据库数据
                            firstShowsTag=false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Show> shows = RoomUtil.getInstance().getRoomInstance().getShowDao().queryShows();
                                    if(!shows.isEmpty()){
                                        ShowList sl = new ShowList();
                                        sl.setShowList(shows);
                                        showListMutableLiveData.postValue(sl);
                                    }
                                }
                            }).start();

                        }
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
