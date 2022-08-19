package com.qxy.demo;

import android.app.Application;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.qxy.demo.utils.UserImformations;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String clientkey = "awo0xxllhhzsflua"; // 需要到开发者网站申请

        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientkey));
    }
}
