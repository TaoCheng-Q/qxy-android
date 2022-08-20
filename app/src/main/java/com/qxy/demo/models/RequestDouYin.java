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

    static String TOKEN_URL = "https://open.douyin.com/oauth/access_token/";

    static String  CLIENT_TOKEN = "https://open.douyin.com/oauth/client_token/";

    static String TOP_LIST="https://open.douyin.com/discovery/ent/rank/item/";

    public static void getTopList(Map<String,String> headerMap,Map<String,String> bodyMap,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        for (String item:
                headerMap.keySet()) {
            if(headerMap.get(item)!=null){
                request.addHeader(item,headerMap.get(item));
            }
        }
        String url = TOP_LIST;
        url = url+"?";
        for (String key: bodyMap.keySet()){
            url = url + key+"="+bodyMap.get(key)+"&";
        }
        url = url.substring(0,url.length()-1);
        request.url(url);
        client.newCall(request.build()).enqueue(callback);
    }

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
