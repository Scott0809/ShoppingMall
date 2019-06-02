package com.bobo.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.utils.UtilsStyle;

/**
 * Created by 求知自学网 on 2019/5/4 Copyright © Leon. All rights reserved.
 * Functions: 商城app的起始页  42考场 - 3号 - 713 上午8：00
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        //设置状态栏上的字体为黑色-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(this,true);

        //2秒中进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行在主线程
                //启动主页面
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));

                //关闭当前页面
                finish();
            }
        },2000);

    }
}
