package com.bobo.shoppingmall.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.utils.UtilsStyle;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.community.fragment.CommunityFragmnet;
import com.bobo.shoppingmall.home.fragment.HomeFragmnet;
import com.bobo.shoppingmall.shoppingcart.fragment.ShoppingCartFragmnet;
import com.bobo.shoppingmall.type.fragment.TypeFragmnet;
import com.bobo.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;

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

    /**存储各个fragment 的集合*/
    private ArrayList<BaseFragment> fragments;

    //用户选择RadioGroup 上按钮位置
    private int position = 0;

    //缓存的fragmnet或者上次显示的fragment
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife和当前activity绑定
        ButterKnife.bind(this);

        //初始化各个fragment
        initFragment();
    }


    @OnClick({R.id.rb_home, R.id.rb_type, R.id.rb_community, R.id.rb_cart, R.id.rb_user, R.id.rg_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_home://主页
                position = 0;
                //设置状态栏上的字体为白色
                UtilsStyle.statusBarLightMode(this,false);
                break;
            case R.id.rb_type://分类
                position = 1;
                //设置状态栏上的字体为黑色
                UtilsStyle.statusBarLightMode(this,true);
                break;
            case R.id.rb_community://发现
                position = 2;
                //设置状态栏上的字体为黑色
                UtilsStyle.statusBarLightMode(this,true);
                break;
            case R.id.rb_cart://购物车
                position = 3;
                //设置状态栏上的字体为黑色
                UtilsStyle.statusBarLightMode(this,true);
                break;
            case R.id.rb_user://用户中心
                position = 4;
                //设置状态栏上的字体为黑色
                UtilsStyle.statusBarLightMode(this,true);
                break;
            default://默认主页
                position = 0;
                //设置状态栏上的字体为黑色
                UtilsStyle.statusBarLightMode(this,true);
                break;
        }

        //根据位置去取不同的fragment
        BaseFragment baseFragment = getFragment(position);
        //切换fragment的方法
        switchFragment(tempFragment,baseFragment);
    }

    //初始化各个fragment
    private void initFragment(){
        fragments = new ArrayList<>();
        fragments.add(new HomeFragmnet());
        fragments.add(new TypeFragmnet());
        fragments.add(new CommunityFragmnet());
        fragments.add(new ShoppingCartFragmnet());
        fragments.add(new UserFragment());
        //默认选择首页
        rbHome.performClick();
        //rgMain.check(R.id.rb_home);
    }

    private BaseFragment getFragment(int position){
        if (fragments != null && fragments.size() > position){
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    /**
     * 切换Fragmengt的方法
     * @param fromFragment 第一个参数：上次显示的fragment
     * @param nextFragment 第二个参数：当前正要显示的fragment
     */
    private void switchFragment(Fragment fromFragment,BaseFragment nextFragment){
        if (tempFragment != nextFragment){
            tempFragment = nextFragment;
            if (nextFragment != null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragmmnet是否添加
                if (!nextFragment.isAdded()){//如果没有添加过添加
                    if (fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                }else {//如果添加过直接show
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }



}


//   切换Fragmengt的方法源代码 总感觉这种方式没有view pager 靠谱
//    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
//        if (mContext != nextFragment) {
//            mContext = nextFragment;
//            if (nextFragment != null) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                //判断nextFragment是否添加
//                if (!nextFragment.isAdded()) {
//                    //隐藏当前Fragment
//                    if (fromFragment != null) {
//                        transaction.hide(fromFragment);
//                    }
//                    transaction.add(R.id.frameLayout, nextFragment).commit();
//                } else {
//                    //隐藏当前Fragment
//                    if (fromFragment != null) {
//                        transaction.hide(fromFragment);
//                    }
//                    transaction.show(nextFragment).commit();
//                }
//            }
//        }
//    }
