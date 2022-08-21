package com.qxy.demo.models;

import android.util.Log;

import com.qxy.demo.utils.CallBackUtil;
import com.qxy.demo.utils.OkhttpUtil;
import com.qxy.demo.utils.UserImformations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestDouYin {

    public static String TOKEN_URL = "https://open.douyin.com/oauth/access_token/";

    public static String  CLIENT_TOKEN = "https://open.douyin.com/oauth/client_token/";

    public static String TOP_LIST="https://open.douyin.com/discovery/ent/rank/item/";

    public static String TOP_LIST_VERSION="https://open.douyin.com/discovery/ent/rank/version/";

    public static String USERINFO="https://open.douyin.com/oauth/userinfo/";

    public static String VIDEO_LIST = "https://open.douyin.com/video/list/";

    public static String FOLLOWING_LIST="https://open.douyin.com/following/list/";

    public static String FANS_LIST="https://open.douyin.com/fans/list/";

    public static String VIDEO_DATA="https://open.douyin.com/video/data/";

    public static int PAGE_COUNT=10;

    public static void post(String url,Map<String,String> bodyMap,Map<String,String> paramMap,Map<String,String> headerMap,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder();
//        header参数
        for (String item:
                headerMap.keySet()) {
            if(headerMap.get(item)!=null){
                request.addHeader(item,headerMap.get(item));
            }
        }
//        String url = TOP_LIST;
//        url参数
        url = url+"?";
        for (String key: paramMap.keySet()){
            if(paramMap.get(key)!=null){
                url = url + key+"="+paramMap.get(key)+"&";
            }
        }
        url = url.substring(0,url.length()-1);
        request.url(url);
//        body参数
        FormBody.Builder requestBody = new FormBody.Builder();
        for (String item: bodyMap.keySet()){
            if(bodyMap.get(item)!=null){
                requestBody.add(item,bodyMap.get(item));
            }
        }
        request.post(requestBody.build());
        client.newCall(request.build()).enqueue(callback);
    }

    public static void getTopListVersion(int cursor,Callback callback){

    }

//    get请求
    public static void getTopList(String url,Map<String,String> headerMap,Map<String,String> bodyMap,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        for (String item:
                headerMap.keySet()) {
            if(headerMap.get(item)!=null){
                request.addHeader(item,headerMap.get(item));
            }
        }
//        String url = TOP_LIST;
        url = url+"?";
        for (String key: bodyMap.keySet()){
            url = url + key+"="+bodyMap.get(key)+"&";
        }
        url = url.substring(0,url.length()-1);
        request.url(url);
        client.newCall(request.build()).enqueue(callback);
    }

//    获取ClientToken请求
    public static void getClientToken(Callback callback){
//        OkhttpUtil
        Map<String,String> bodyMap = new HashMap<>();
        bodyMap.put("client_key",UserImformations.getInstance().getKey());
        bodyMap.put("client_secret",UserImformations.getInstance().getSecret());
        bodyMap.put("grant_type","client_credential");
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","multipart/form-data");
        requestToken(CLIENT_TOKEN,bodyMap,headerMap,callback);
    }

//    封装的post请求
    public static void requestToken(String url, Map<String, String> bodyMap, Map<String, String> headerMap, Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder body = new FormBody.Builder();
        for (String item: bodyMap.keySet()) {
            if(bodyMap.get(item)!=null){
                body.add(item,bodyMap.get(item));
            }

        }
//        while (bodyMap.entrySet().iterator().hasNext()){
//            Map.Entry<String, String> item = bodyMap.entrySet().iterator().next();
//            body.add(item.getKey(), item.getValue());
//        }
        Request.Builder request = new Request.Builder()
                .url(url)
                .post(body.build());
        for (String item:headerMap.keySet()) {
            if(headerMap.get(item)!=null){
                request.addHeader(item,headerMap.get(item));
            }

        }
        client.newCall(request.build()).enqueue(callback);
    }


//    获取AccessToken请求
    public static void requestAccessToken(Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("client_key", UserImformations.getInstance().getKey())
                .add("client_secret",UserImformations.getInstance().getSecret())
                .add("grant_type","authorization_code")
                .add("code",UserImformations.getInstance().getCode())
                .build();
        final Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body).build();
        client.newCall(request).enqueue(callback);
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                try {
////                Log.d("TAG", "onResponse: "+response.body().string());
//                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    JSONObject json = new JSONObject(jsonObject.getString("data"));
//                    UserImformations.getInstance().setToken(json.getString("access_token"));
//                    UserImformations.getInstance().setOpen_id(json.getString("open_id"));
//                    UserImformations.getInstance().setRefresh_token(json.getString("refresh_token"));
//                    Log.d("TAG", "onResp: "+"token"+UserImformations.getInstance().getToken()+"//open-id"+UserImformations.getInstance().getOpen_id()+"//"+UserImformations.getInstance().getCode());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

public void requestToken(){
    OkHttpClient client = new OkHttpClient();
    RequestBody body = new FormBody.Builder()
            .add("client_key", UserImformations.getInstance().getKey())
            .add("client_secret",UserImformations.getInstance().getSecret())
            .add("grant_type","authorization_code")
            .add("code",UserImformations.getInstance().getCode())
            .build();
    final Request request = new Request.Builder()
            .url(TOKEN_URL)
            .post(body).build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            try {
//                Log.d("TAG", "onResponse: "+response.body().string());
                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONObject json = new JSONObject(jsonObject.getString("data"));
                UserImformations.getInstance().setToken(json.getString("access_token"));
                UserImformations.getInstance().setOpen_id(json.getString("open_id"));
                UserImformations.getInstance().setRefresh_token(json.getString("refresh_token"));
                Log.d("TAG", "onResp: "+"token"+UserImformations.getInstance().getToken()+"//open-id"+UserImformations.getInstance().getOpen_id()+"//"+UserImformations.getInstance().getCode());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    });
}



//    Map<String,String> stringMap = new HashMap<>();
//    stringMap.put("Content-Type","application/x-www-form-urlencoded");
////    JSONObject bodyJson = new JSONObject();
////    try {
////        bodyJson.put("client_key", UserImformations.getInstance().getKey());
////        bodyJson.put("client_secret",UserImformations.getInstance().getSecret());
////        bodyJson.put("grant_type","authorization_code");
////        bodyJson.put("code",UserImformations.getInstance().getCode());
////    } catch (JSONException e) {
////        e.printStackTrace();
////    }
//    String body = "client_key="+UserImformations.getInstance().getKey()+"&client_secret="
//            +UserImformations.getInstance().getSecret()
//            +"&grant_type=authorization_code&code="+UserImformations.getInstance().getCode();
//
//    OkhttpUtil.okHttpPostJson(TOKEN_URL,body , stringMap, new CallBackUtil.CallBackString() {
//        @Override
//        public void onFailure(Call call, Exception e) {
//
//        }
//
//        @Override
//        public void onResponse(String response) {
//            Log.d("TAG", "onResponse: "+response);
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                JSONObject json = new JSONObject(jsonObject.getString("data"));
//                UserImformations.getInstance().setToken(json.getString("access_token"));
//                UserImformations.getInstance().setOpen_id(json.getString("open_id"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    });
//}
}
