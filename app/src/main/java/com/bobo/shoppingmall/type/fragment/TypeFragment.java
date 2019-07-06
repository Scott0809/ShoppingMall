package com.bobo.shoppingmall.type.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UtilsStyle;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 类型页面的fragmengt
 */
public class TypeFragment extends BaseFragment {

    @Bind(R.id.tl_1)
    SegmentTabLayout segmentTabLayout;
    @Bind(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @Bind(R.id.fl_type)
    FrameLayout flType;

    //用来装字fragment的容器
    private List<BaseFragment> fragmentList;

    //切换fragment的时候用到的临时fragment
    private Fragment tempFragment;

    //分类页面
    public ListFragment listFragment;

    //标签页面
    public TagFragment tagFragment;

    //用户上次选中的position 默认为0
    private int onTabSelectPosition = 0;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_type, null);

        //美化状态栏
        statusBarSystemSet(view);

        return view;
    }

    @Override
    public void initData() {
        initFragment();

        String[] titles = {"分类", "标签"};

        segmentTabLayout.setTabData(titles);

        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(tempFragment, fragmentList.get(position));

                //解决TypeFragment（本页面）Tab 与fragment 错乱的bug
                onTabSelectPosition = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
       // switchFragment(tempFragment, fragmentList.get(0));
        //解决TypeFragment（本页面）Tab 与fragment 错乱的bug
        switchFragment(tempFragment, fragmentList.get(onTabSelectPosition));
    }

    public void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.add(R.id.fl_type, nextFragment, "tagFragment").commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();

        listFragment = new ListFragment();
        tagFragment = new TagFragment();

        fragmentList.add(listFragment);
        fragmentList.add(tagFragment);

        //解决TypeFragment（本页面）Tab 与fragment 错乱的bug
        switchFragment(tempFragment, fragmentList.get(onTabSelectPosition));
       // onNavigationItemSelected(position);
    }


    //-------没有用到------https://blog.csdn.net/yuzhiqiang_1993/article/details/75014591---------------------
    /*添加fragment*/
    private void addFragment(Fragment fragment) {

        /*判断该fragment是否已经被添加过  如果没有被添加  则添加*/
        if (!fragment.isAdded()) {
            //原来写法 安装好一段时间用户按home键退出app有 fragment 重叠的情况
            // getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).commit();
            /*添加到 fragmentList*/
            //fragmentList.add(fragment);
            if (fragment == listFragment){
                //tag 用于 安装好一段时间用户按home键退出 再进不必再创建
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment,
                        "list").commit();
            }

            if (fragment == tagFragment) {
                //加了tag 用于 安装好一段时间用户按home键退出 再进不必再创建
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment,
                        "tag").commit();
            }
        }
    }

    /*显示fragment*/
    private void showFragment(Fragment fragment) {

        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                /*先隐藏其他fragment*/
                getActivity().getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
        getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }


    //根据索引 创建fragment 并 显示
    public void onNavigationItemSelected(int item) {

        switch (item) {
            case 0:
//                addFragment(homeFragment);
//                showFragment(homeFragment);
                break;

            case 1:
//                addFragment(typeFragment);
//                showFragment(typeFragment);
                break;

        }
    }
    //----------------------------------------------------------------------------------------------

    //对不同的安卓手机状态栏透明 字体颜色为黑色/白色处理
    private void statusBarSystemSet(View view) {

        //动态计算statusBar高度 交给各个fragmnet实现因为各个fragment的状态栏颜色不一样
        StBarUtil.setOccupationHeight(getActivity(), view);

        //设置状态栏上的字体为黑色（OV系手机的状态栏字体是白色）-因为本页面是白色必须设置
        UtilsStyle.statusBarLightMode(getActivity(),true);
    }

}
