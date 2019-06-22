package com.bobo.shoppingmall.community.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.community.adapter.CommunityViewPagerAdapter;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UtilsStyle;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 发现页面的fragmengt
 */
public class CommunityFragmnet extends BaseFragment {


    private ImageButton ibCommunityIcon;

    private ImageButton ibCommunityMessage;

    private ViewPager viewPager;

    private TabLayout tablayout;


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        //美化状态栏
        statusBarSystemSet(view);
        ibCommunityIcon = (ImageButton) view.findViewById(R.id.ib_community_icon);
        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        ibCommunityMessage = (ImageButton) view.findViewById(R.id.ib_community_message);

        CommunityViewPagerAdapter adapter = new CommunityViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setVisibility(View.VISIBLE);
        tablayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "发现页面fragment的数据被初始化了  initData()");
    }

    //对不同的安卓手机状态栏透明 字体颜色为黑色/白色处理
    private void statusBarSystemSet(View view) {

        //动态计算statusBar高度 交给各个fragmnet实现因为各个fragment的状态栏颜色不一样
        StBarUtil.setOccupationHeight(getActivity(), view);

        //设置状态栏上的字体为黑色（OV系手机的状态栏字体是白色）-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(getActivity(),true);
    }

}
