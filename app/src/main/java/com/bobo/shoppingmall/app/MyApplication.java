package com.bobo.shoppingmall.app;

import android.app.Application;


import com.bobo.shoppingmall.HttpsUtils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 应用程序类 系统默认单例
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //配置 OkHttpUtils
        //initOkhttpClient();

    }

    //配置 OkHttpUtils https://github.com/hongyangAndroid/okhttputils
    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)//连接超时
                .readTimeout(10000L, TimeUnit.MILLISECONDS)//读取超时
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
