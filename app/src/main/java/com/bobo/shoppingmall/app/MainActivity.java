package com.bobo.shoppingmall.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.utils.Constants;
import com.bobo.shoppingmall.utils.LELog;
import com.bobo.shoppingmall.utils.UtilsStyle;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.community.fragment.CommunityFragmnet;
import com.bobo.shoppingmall.home.fragment.HomeFragmnet;
import com.bobo.shoppingmall.shoppingcart.fragment.ShoppingCartFragmnet;
import com.bobo.shoppingmall.type.fragment.TypeFragment;
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

    //切换页面fragment重叠的bug 2019-6-29修改为 默认=null
    //private Fragment tempFragment;

    //缓存的fragmnet或者上次显示的fragment 默认=null
    private Fragment tempFragment = null;

    //广播-用户每次 切换到分类fragment 刷新数据
    private static LocalBroadcastManager mLBM;

    /**外界调用 切换fragment的接口*/
    private OnSwitchFragment onSwitchFragment;

    //购物车 fragment
    private ShoppingCartFragmnet shoppingCartFragmnet;

    //是否切换到购物车
    private boolean isGoingToShoppingCart = false;


    //接收到切换了页面（fragment）广播的处理 切换到 购物车fragment
    private BroadcastReceiver goingToShoppingCart = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            /**
             * Can not perform this action after onSaveInstanceState
             * 无法在onSaveInstanceState之后执行此操作
             * 在GoodsInfoActivity 中直接 finish(); 紧接着就执行下面的方法是不可以的
             * 要在 activity生命周期 内调用才行 所以定义一个变量 activity “醒来”
             * onResume() 再调用
             */
            //rbCart.performClick();
            //onViewClicked(rbCart);

            isGoingToShoppingCart = true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        if (isGoingToShoppingCart){
            rbCart.performClick();
            onViewClicked(rbCart);
            isGoingToShoppingCart = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife和当前activity绑定
        ButterKnife.bind(this);

        //初始化各个fragment
        initFragment();

        //创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(this);

        //定义接收广播的方法-接收到后切换到购物车fragment
        mLBM.registerReceiver(goingToShoppingCart,new IntentFilter(Constants.GOINGTOTHESHOPPINGCART));

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
                //发送切换了fragment的广播
                mLBM.sendBroadcast(new Intent(Constants.UPDATE_TYPE_DATA));
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
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragmnet());
        shoppingCartFragmnet = new ShoppingCartFragmnet();
        fragments.add(shoppingCartFragmnet);
        //fragments.add(new ShoppingCartFragmnet());
        fragments.add(new UserFragment());
        //默认选择首页
        rbHome.performClick();
        //rgMain.check(R.id.rb_home);

        //2019-6-23增加去逛逛跳转到首页
        initFragmentSwithListener();
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


    //2019-6-23增加去逛逛跳转到首页
    private void initFragmentSwithListener(){

        //购物车跳转 到指定的fragment 首页
        shoppingCartFragmnet.setOnSwitchFragment(new OnSwitchFragment() {
            @Override
            public void PerformClickRadioButton(int i) {
                switch (i){
                    case 0://跳转到首页
                        rbHome.performClick();
                        onViewClicked(rbHome);
                        break;
                    case 2://跳转到购物车
                        rbCart.performClick();
                        onViewClicked(rbCart);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {

        //注册的广播（接收方）一定要关掉
        mLBM.unregisterReceiver(goingToShoppingCart);

        super.onDestroy();
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
