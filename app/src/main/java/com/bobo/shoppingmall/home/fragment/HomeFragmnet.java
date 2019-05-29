package com.bobo.shoppingmall.home.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 求知自学网 on 2019/5/11. Copyright © Leon. All rights reserved.
 * Functions: 主页面的fragmengt
 */
public class HomeFragmnet extends BaseFragment {

    @Bind(R.id.tv_search_home)
    TextView tvSearchHome;
    @Bind(R.id.tv_message_home)
    TextView tvMessageHome;
    @Bind(R.id.rv_home)
    RecyclerView rvHome;
    @Bind(R.id.ib_top)
    ImageButton ibTop;


    private HomeFragmentAdapter adapter;

    /**
     * 首页数据对象
     */
    private ResultBeanData.ResultBean resultBean;

    @Override
    public void onResume() {
        super.onResume();

        //每次进入首页判断是否有新版本
        if (getContext() != null && getActivity() != null){
            UpdateUtils.checkforUpdate(getContext(),getActivity());
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "主页面fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);

        //解决高版本手机状态栏上文字 和 titleBar重叠的问题
        StBarUtil.setOccupationHeight(getActivity(),view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //网络请求
        getDataFromNet();
    }

    private void getDataFromNet() {

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
                //LELog.showLogWithLineNum(5,e.getMessage());
            }

            /**
             * 当联网成功的时候回调
             * @param response  请求成功的数据
             * @param id
             */
            @Override
            public void onResponse(String response, int id) {
                //LELog.showLogWithLineNum(5,"请求成功"+response);
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
            adapter = new HomeFragmentAdapter(mContext, resultBean);
            rvHome.setAdapter(adapter);

            GridLayoutManager manager = new GridLayoutManager(mContext, 1);

            //设置跨度大小的监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if (position <= 3){
                        //隐藏返回顶部的按钮
                        ibTop.setVisibility(View.GONE);
                    }else{
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
}
