package com.bobo.shoppingmall.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.shoppingmall.home.bean.ResultBeanData;

/**
 * Created by 求知自学网 on 2019/5/12. Copyright © Leon. All rights reserved.
 * Functions: 首页RecyclerView 的适配器
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter {

    /**广告条幅类型*/
    public static final int BANNER = 0;

    /**频道类型*/
    public static final int CHANNEL = 1;

    /**活动类型*/
    public static final int ATC = 2;

    /**秒杀类型*/
    public static final int SECKILL = 3;

    /**推荐类型*/
    public static final int RECOMMEND = 4;

    /**热卖类型*/
    public static final int HOT = 5;

    /**当前类型默认是0*/
    private int currentType = BANNER;

    private Context mContext;
    //数据对象
    private ResultBeanData mResultBeanData;
    //用LayoutInflater初始化布局 和 View.inflate()类似
    private LayoutInflater mLayoutInflater;

    public HomeFragmentAdapter(Context context, ResultBeanData resultBeanData) {
        this.mContext = context;
        this.mResultBeanData = resultBeanData;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于BaseAdapter的getView方法 创建ViewHolder部分代码
     * 创建viewHolder
     * @param viewGroup
     * @param i 当前的类型
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    /**
     * 相当于BaseAdapter的getView方法中的绑定数据模块
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    /**
     * 根据索引的到item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        switch (position){
            case BANNER:
                currentType  = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ATC:
                currentType = ATC;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /**
     * 总共有多少个item
     * @return
     */
    @Override
    public int getItemCount() {

        //开发过程中从1-->6
        return 1;
    }
}
