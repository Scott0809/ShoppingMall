package com.bobo.shoppingmall.type.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bobo.shoppingmall.base.BaseFragment;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 类型页面的fragmengt
 */
public class TypeFragmnet extends BaseFragment {

    private TextView textView;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        Log.e("TAG","类型页面fragment的UI被初始化了");

        textView = new TextView(mContext);

        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG","类型页面fragment的数据被初始化了");
        textView.setText("类型页面内容");
    }
}
