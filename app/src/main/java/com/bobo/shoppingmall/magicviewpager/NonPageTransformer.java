package com.bobo.shoppingmall.magicviewpager;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by 求知自学网 on 2019/5/19 Copyright © Leon. All rights reserved.
 * Functions:  中间大两边小的 view pager 动画属性部分来自github
 */
public class NonPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(View page, float position)
    {
        page.setScaleX(0.999f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}
