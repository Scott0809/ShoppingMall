package com.bobo.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.utils.UtilsStyle;

/**
 * Created by 求知自学网 on 2019/5/4 Copyright © Leon. All rights reserved.
 * Functions: 商城app的起始页  42考场 - 3号 - 713 上午8：00
 */
public class WelcomeActivity extends Activity {

    private final static int SPLASH = 2019620;

    //2秒中进入主页面
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            //如果当前的activity 已经退出那么不做任何的处理
            if (isFinishing()){
                return;
            }

            //启动主页面
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            //关闭当前页面
            finish();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        //设置状态栏上的字体为黑色-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(this,true);

        //2秒中进入主页面 阿福老师这样写的有bug 退出页面 handle 还在起作用会自己打开app
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //执行在主线程
//                //启动主页面
//                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//
//                //关闭当前页面
//                finish();
//            }
//        },2000);

        //handle发送一个1秒钟延时的消息
        handler.sendEmptyMessageDelayed(SPLASH, 1600);
    }

    @Override
    protected void onDestroy() {

        //当activity销毁的时候移除掉handle发送的消息合理的管理内存
        handler.removeCallbacksAndMessages(null);

        super.onDestroy();
    }
}
