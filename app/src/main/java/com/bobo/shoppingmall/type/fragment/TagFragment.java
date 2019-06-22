package com.bobo.shoppingmall.type.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.httpsUtils.OkHttpUtils;
import com.bobo.shoppingmall.httpsUtils.callback.StringCallback;
import com.bobo.shoppingmall.type.bean.TagBean;
import com.bobo.shoppingmall.utils.Constants;
import com.bobo.shoppingmall.base.BaseFragment;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends BaseFragment {

    private GridView gv_tag;
    private com.bobo.shoppingmall.type.adapter.TagGridViewAdapter adapter;
    private List<TagBean.ResultBean> result;


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        gv_tag = (GridView) view.findViewById(R.id.gv_tag);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //Leon增加在重新开始的时候起到一个刷新数据的效果
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }


    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.TAG_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
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
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);
                        adapter = new com.bobo.shoppingmall.type.adapter.TagGridViewAdapter(mContext, result);
                        gv_tag.setAdapter(adapter);
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
        com.bobo.shoppingmall.type.bean.TagBean tagBean = gson.fromJson(json, com.bobo.shoppingmall.type.bean.TagBean.class);
        result = tagBean.getResult();
    }

}
