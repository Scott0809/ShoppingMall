package com.bobo.shoppingmall.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bobo.shoppingmall.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 求知自学网 on 2019/5/5 Copyright © Leon. All rights reserved.
 * Functions: 主页面 放 fragment的 activity  FragmentActivity  用AppCompatActivity 也能实现
 */
public class MainActivity extends FragmentActivity {

    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_type)
    RadioButton rbType;
    @Bind(R.id.rb_community)
    RadioButton rbCommunity;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife和当前activity绑定
        ButterKnife.bind(this);

       rgMain.check(R.id.rb_home);

    }


    @OnClick({R.id.rb_home, R.id.rb_type, R.id.rb_community, R.id.rb_cart, R.id.rb_user, R.id.rg_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                break;
            case R.id.rb_type:
                break;
            case R.id.rb_community:
                break;
            case R.id.rb_cart:
                break;
            case R.id.rb_user:
                break;
            case R.id.rg_main:
                break;
        }
    }
}
