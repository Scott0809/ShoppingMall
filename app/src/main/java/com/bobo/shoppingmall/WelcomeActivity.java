package com.bobo.shoppingmall;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.app.MainActivity;

/**
 * Created by 求知自学网 on 2019/5/4 Copyright © Leon. All rights reserved.
 * Functions: 商城app的起始页
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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
