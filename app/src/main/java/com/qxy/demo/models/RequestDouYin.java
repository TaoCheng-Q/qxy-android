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

    String TOKEN_URL = "https://open.douyin.com/oauth/access_token/";

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
