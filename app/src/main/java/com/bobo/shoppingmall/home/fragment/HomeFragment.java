package com.bobo.shoppingmall.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.bobo.shoppingmall.home.bean.ResultBeanData;
import com.bobo.shoppingmall.httpsUtils.OkHttpUtils;
import com.bobo.shoppingmall.httpsUtils.callback.StringCallback;
import com.bobo.shoppingmall.utils.Constants;
import com.bobo.shoppingmall.utils.StBarUtil;
import com.bobo.shoppingmall.utils.UpdateUtils;
import com.bobo.shoppingmall.utils.UtilsStyle;
import com.bobo.shoppingmall.weiget.LEloadingView;
import com.bobo.shoppingmall.weiget.MyPtrClassicFrameLayout;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 主页面的fragmengt
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.tv_search_home)
    TextView tvSearchHome;
    @Bind(R.id.tv_message_home)
    TextView tvMessageHome;
    @Bind(R.id.rv_home)
    RecyclerView rvHome;
    @Bind(R.id.ib_top)
    ImageButton ibTop;

    /**第三方下拉刷新控件 已经停止维护*/
    private PtrClassicFrameLayout mPtrFrame;

    /**
     * home（本）页面的 adapter
     */
    private HomeFragmentAdapter adapter;

    /**
     * 当网络请求时的loading动画
     */
    private KProgressHUD mKProgressHUD;

    /**
     * 首页数据对象
     */
    private ResultBeanData.ResultBean resultBean;

    /**网络请求完成的变量-默认为true*/
    private boolean isSuccess = true;

    @Override
    public void onResume() {
        super.onResume();

        //每次进入首页判断是否有新版本
        if (getContext() != null && getActivity() != null) {
            UpdateUtils.checkforUpdate(getContext(), getActivity());
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "主页面fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        mPtrFrame = (PtrClassicFrameLayout)view.findViewById(R.id.ptr_frame);

        //美化状态栏
        statusBarSystemSet(view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //网络请求
        getDataFromNet();

        //设置下拉刷新
        setPullToRefres();
    }

    private void getDataFromNet() {

        //开始网络请求-开始显示loading
        mKProgressHUD = KProgressHUD.create(getContext())
                .setCustomView(new LEloadingView(getContext()))
                .setLabel("Please wait", Color.GRAY)
                .setBackgroundColor(Color.WHITE)
                .show();

        //联网请求 老弟商城项目url：https://geekpark.site/atguigu/json/WENJU_STORE.json
        String url = Constants.HOME_URL;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {

            /**
             * 当请求失败的时候回调
             * @param call
             * @param e
             * @param id
             */
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getContext(), "请求失败请检查网络", Toast.LENGTH_SHORT).show();

                //结束下拉刷新（无论成功失败本次发起请求已经结束）
                mPtrFrame.refreshComplete();

                //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                isSuccess = true;

                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();
            }

            /**
             * 当联网成功的时候回调
             * @param response  请求成功的数据
             * @param id
             */
            @Override
            public void onResponse(String response, int id) {
                //LELog.showLogWithLineNum(5,"请求成功"+response);

                //结束下拉刷新（无论成功失败本次发起请求已经结束）
                mPtrFrame.refreshComplete();

                //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
                isSuccess = true;

                //loading结束（无论成功失败本次发起请求已经结束）
                mKProgressHUD.dismiss();

                //解析数据
                processedData(response);
            }
        });
    }

    //使用阿里开源的框架法fastjson解析数据
    private void processedData(String json) {

        //解析数据 注意gsonfromt 生成的是fastjson数据
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);

        //首页数据模型对象
        resultBean = resultBeanData.getResult();

        if (resultBean != null) {//有数据

            //设置适配器
            if (adapter == null) {
                adapter = new HomeFragmentAdapter(mContext, resultBean);
                rvHome.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

            //原来
            //adapter = new HomeFragmentAdapter(mContext, resultBean);
            //rvHome.setAdapter(adapter);

            GridLayoutManager manager = new GridLayoutManager(mContext, 1);

            //设置跨度大小的监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if (position <= 3) {
                        //隐藏返回顶部的按钮
                        ibTop.setVisibility(View.GONE);
                    } else {
                        //显示返回顶部的按钮
                        ibTop.setVisibility(View.VISIBLE);
                    }

                    //注意这里要返回1 返回0 则什么都不显示比1大报错
                    return 1;
                }
            });

            //设置布局管理者 注意RecyclerView 要设置
            rvHome.setLayoutManager(manager);

        } else {//没有数据

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Toast.makeText(getContext(), "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(getContext(), "消息中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                //让RecyclerView 滚动到顶部
                rvHome.scrollToPosition(0);
                break;
        }
    }

    //对不同的安卓手机状态栏透明 字体颜色为黑色/白色处理
    private void statusBarSystemSet(View view) {

        //动态计算statusBar高度 交给各个fragmnet实现因为各个fragment的状态栏颜色不一样
        StBarUtil.setOccupationHeight(getActivity(), view);

        //设置状态栏上的字体为白色（OV系手机的状态栏字体是白色）
        UtilsStyle.statusBarLightMode(getActivity(), false);
    }

    //Leon增加下拉刷新
    private void setPullToRefres(){
        //mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                //判断网络是否请求成功避免网络不好的情况下连续不断的请求
                if (isSuccess){//如果上次请求成功
                    //当前正在请求变量置为false
                    isSuccess = false;
                    //下拉刷新-网络请求拿到最新的数据
                    getDataFromNet();
                }
            }

            /**
             * 当对RecycleView列表进行下拉刷新，需要对该方法进行处理
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                //return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                //根据自身的情况修改后
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rvHome, header);
            }
        });
    }

}
