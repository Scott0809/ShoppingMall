package com.bobo.shoppingmall.type.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.httpsUtils.OkHttpUtils;
import com.bobo.shoppingmall.httpsUtils.callback.StringCallback;
import com.bobo.shoppingmall.type.adapter.TypeLeftAdapter;
import com.bobo.shoppingmall.type.adapter.TypeRightAdapter;
import com.bobo.shoppingmall.type.bean.TypeBean;
import com.bobo.shoppingmall.utils.CacheUtils;
import com.bobo.shoppingmall.utils.Constants;
import com.bobo.shoppingmall.base.BaseFragment;
import com.bobo.shoppingmall.utils.LELog;
import com.bobo.shoppingmall.weiget.LEloadingView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 分类页面
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment {
    private FrameLayout fl_list_container;
    private ListView lv_left;
    private RecyclerView rv_right;
    private List<TypeBean.ResultBean> result;

    /**持久化保存用户上次选中的结果的key*/
    private static final String LASTLEFTPOAITION = "lastLeftPoaition";

    //接收用户切换了页面（fragment）的广播- 注意接收一定onDestroy() 关闭
    protected LocalBroadcastManager mLBM;

    //左边list view的适配
    private TypeLeftAdapter leftAdapter;

    //右边recycleview的适配器
    TypeRightAdapter rightAdapter;

    private boolean isFirst = true;

    //是否切换页面 接收到切换页面的广播 和onResume都算 默认为false
    private boolean isSwitchPages = false;

    /**
     * 当网络请求时的loading动画
     */
    private KProgressHUD mKProgressHUD;

    //接收到切换了页面（fragment）广播的处理 leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↓
    private BroadcastReceiver WantUpdateData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //再次请求网络
            getDataFromNet(urls[0]);
            //接收到切换页面的广播 和onResume都算 切换了页面
            isSwitchPages = true;
            LELog.showLogWithLineNum(5,"BroadcastReceiver");
        }
    };
    //leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↑

    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};



    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        lv_left = (ListView) view.findViewById(R.id.lv_left);
        rv_right = (RecyclerView) view.findViewById(R.id.rv_right);

        //注册广播 leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↓
        mLBM = LocalBroadcastManager.getInstance(getActivity());

        //定义接收广播的方法
        mLBM.registerReceiver(WantUpdateData,new IntentFilter(Constants.UPDATE_TYPE_DATA));
        //leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↑

        return view;
    }


    //leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↓
    @Override
    public void onResume() {
        super.onResume();

        //Leon增加在重新开始的时候起到一个刷新数据的效果、
        getDataFromNet(urls[0]);

        //接收到切换页面的广播 和onResume都算
        isSwitchPages = true;

        LELog.showLogWithLineNum(5,"onResume()");
    }
    //leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↑


    @Override
    public void initData() {
        super.initData();
        //联网请求 leon 注释了这里 增加 用户切换页面后数据刷新仍然 选中原来的数据 ↓
        //getDataFromNet(urls[0]);
    }

    /**
     * 具体的联网请求代码
     * @param url
     */
    public void getDataFromNet(String url) {

        OkHttpUtils
                .get()
                .url(url)
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
        }

        @Override
        public void onResponse(String response, int id) {

            //联网请求成功 loading结束（无论成功失败本次发起请求已经结束）
            mKProgressHUD.dismiss();

            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        //解析数据
                        processData(response);
                        if (isFirst) {
                            leftAdapter = new TypeLeftAdapter(mContext);
                            lv_left.setAdapter(leftAdapter);
                        }


                        initListener(leftAdapter);

                        //解析右边数据
                        rightAdapter = new TypeRightAdapter(mContext, result);
                        rv_right.setAdapter(rightAdapter);

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);

                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                if (position == 0) {
                                    return 3;
                                } else {
                                    return 1;
                                }
                            }
                        });
                        rv_right.setLayoutManager(manager);
                    }

                    //leon增加用户每一次切换页面增加刷新数据和UI逻辑↓
                    int lastLeftPoaition = CacheUtils.getIntHaveDefaultValue(mContext,LASTLEFTPOAITION,
                            -1);
                    if (lastLeftPoaition != -1 && isSwitchPages){

                        //View view, int position, long id  用代码点击item像用户的手一样（点击后自己会刷新）
                        lv_left.performItemClick(lv_left.getChildAt(lastLeftPoaition),lastLeftPoaition,
                                lv_left.getItemIdAtPosition(lastLeftPoaition));

                        //接收到切换页面的广播 和onResume都算 处理后改为false 不然会连续不断的调用这里
                        isSwitchPages = false;
                        //Toast.makeText(mContext,"用户上次选中左边"+(lastLeftPoaition+1),
                                //Toast.LENGTH_SHORT).show();
                    }
                    //leon增加用户每一次切换页面增加刷新数据和UI逻辑↑

                    break;
                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void initListener(final TypeLeftAdapter adapter) {
        //点击监听
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新
                if (position != 0) {
                    isFirst = false;
                }
                getDataFromNet(urls[position]);
                leftAdapter.notifyDataSetChanged();

                //leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↓
                CacheUtils.setInt(mContext,LASTLEFTPOAITION,position);
                //leon增加 用户切换页面后数据刷新仍然 选中原来的数据 ↑
            }
        });

        //选中监听
        lv_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新

                //Leon在这里写了toast 感觉 这里不会被调用
                Toast.makeText(mContext,"选中监听"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void processData(String json) {
        Gson gson = new Gson();
        TypeBean typeBean = gson.fromJson(json, TypeBean.class);
        result = typeBean.getResult();
    }

    @Override
    public void onDestroy() {

        //注册的广播（接收方）一定要关掉
        mLBM.unregisterReceiver(WantUpdateData);

        super.onDestroy();
    }
}