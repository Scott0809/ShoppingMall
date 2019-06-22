package com.bobo.shoppingmall.community.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.community.adapter.HotPostListViewAdapter;
import com.bobo.shoppingmall.community.bean.HotPostBean;
import com.bobo.shoppingmall.httpsUtils.OkHttpUtils;
import com.bobo.shoppingmall.httpsUtils.callback.StringCallback;
import com.bobo.shoppingmall.utils.Constants;
import com.bobo.shoppingmall.weiget.LEloadingView;
import com.bobo.shoppingmall.weiget.MyPtrClassicFrameLayout;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/10/6.
 */
public class HotPostFragment extends BaseFragment {

    @Bind(R.id.lv_hot_post)
    ListView lv_hot_post;
    @Bind(R.id.ptr_hot_post)
    MyPtrClassicFrameLayout mPtrFrame;


    private List<HotPostBean.ResultBean> result;

    /**
     * 当网络请求时的loading动画
     */
    private KProgressHUD mKProgressHUD;

    /**网络请求完成的变量-默认为true*/
    private boolean isSuccess = true;


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_hot_post, null);
        //lv_hot_post = (ListView) view.findViewById(R.id.lv_hot_post);
        return view;
    }

    @Override
    public void initData() {

        //设置网络请求
        getDataFromNet();

        //设置下拉刷新
        setPullToRefres();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOT_POST_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());

        //开始网络请求-开始显示loading
        mKProgressHUD = KProgressHUD.create(getContext())
                .setCustomView(new LEloadingView(getContext()))
                .setLabel("Please wait", Color.GRAY)
                .setBackgroundColor(Color.WHITE)
                .show();
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

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
            Toast.makeText(mContext, "请求失败请检查网络", Toast.LENGTH_SHORT).show();
            //loading结束（无论成功失败本次发起请求已经结束）
            mKProgressHUD.dismiss();

            //结束下拉刷新（无论成功失败本次发起请求已经结束）
            mPtrFrame.refreshComplete();

            //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
            isSuccess = true;
        }

        @Override
        public void onResponse(String response, int id) {

            //loading结束（无论成功失败本次发起请求已经结束）
            mKProgressHUD.dismiss();

            //结束下拉刷新（无论成功失败本次发起请求已经结束）
            mPtrFrame.refreshComplete();

            //当前正请求已经结束（无论成功失败本次发起请求已经结束）变量置为true
            isSuccess = true;

            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);
                        HotPostListViewAdapter adapter = new HotPostListViewAdapter(mContext, result);
                        lv_hot_post.setAdapter(adapter);
                    }
                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String json) {
        Gson gson = new Gson();
        HotPostBean hotPostBean = gson.fromJson(json, HotPostBean.class);
        result = hotPostBean.getResult();
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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_hot_post, header);
            }
        });
    }
}
