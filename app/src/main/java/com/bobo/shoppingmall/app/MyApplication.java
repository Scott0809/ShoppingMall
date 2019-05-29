package com.bobo.shoppingmall.app;

import android.app.Application;


import com.bobo.shoppingmall.httpsUtils.OkHttpUtils;
import com.bobo.shoppingmall.utils.SpUtils;
import com.bobo.shoppingmall.utils.UpdateUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
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

        //初始化极光推送
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);


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

    //app被杀了，你监听application的onDestroy方法就行@Override
        public void onTerminate() {
            // 程序终止的时候执行
            //下载成功和失败 是否下载过都要变为false
            SpUtils.setBoolean(this, UpdateUtils.DOWNLOADING,false);
            super.onTerminate();
        }


}
