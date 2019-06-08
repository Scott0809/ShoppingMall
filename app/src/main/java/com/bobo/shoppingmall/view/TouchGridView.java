package com.bobo.shoppingmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bobo.shoppingmall.utils.LELog;


/**
 * Created by 求知自学网 on 2019/5/12. Copyright © Leon. All rights reserved. 
 * Functions: 金山大佬的解决方案 - 安卓4.4之后滑动屏幕 recycleview中的gridview点击无效 （没有用到）
 */
public class TouchGridView extends GridView {


    /**
     * Binary XML file line #39: Binary XML file line #39: Error inflating class com.bobo.shoppingm
     * all.view.TouchGridView
     * 继承View时要要实现它所有的构造函数。
     */
    public TouchGridView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public TouchGridView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当点击 gridview的item的时候这个方法会调用1次
        LELog.showLogWithLineNum(5,"1");

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //当点击 gridview的item的时候这个方法会调用2次
        LELog.showLogWithLineNum(5,"2");

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //当点击 gridview的item的时候这个方法会调用2次
        LELog.showLogWithLineNum(5,"3");

        return super.dispatchTouchEvent(ev);
    }



}
