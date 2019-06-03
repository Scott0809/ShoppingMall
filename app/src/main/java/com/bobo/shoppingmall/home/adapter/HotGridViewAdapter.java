package com.bobo.shoppingmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.home.bean.ResultBeanData;
import com.bobo.shoppingmall.utils.LELog;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.OnClick;

import static com.bobo.shoppingmall.utils.Constants.BASE_URL_IMAGE;

/**
 * Created by 求知自学网 on 2019/5/25. Copyright © Leon. All rights reserved.
 * Functions: 热销下的 GridView 适配器
 */
public class HotGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ResultBeanData.ResultBean.HotInfoBean> datas;


    /**自定义 点击事件接口 GridView 在recycleview中 setOnItemClickListener不响应*/
    private OnHotGridView onHotGridView;


    public HotGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.mContext = mContext;
        this.datas = hot_info;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;


        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_hot = (ImageView)convertView.findViewById(R.id.iv_hot);
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            //绑定（设置）tag 将viewHolder设置给convertView,绑定起来
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHotGridView != null){
                    onHotGridView.onItemClick(position);
                }
            }
        });

        //根据本位置得到对应的数据
        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = datas.get(position);
        Glide.with(mContext).load(BASE_URL_IMAGE+hotInfoBean.getFigure()).placeholder(R.drawable
                .top_btn).into(viewHolder.iv_hot);
        viewHolder.tv_name.setText(hotInfoBean.getName());
        viewHolder.tv_price.setText("￥"+hotInfoBean.getCover_price());

        return convertView;
    }


   class ViewHolder{
        //商品的封面
        ImageView iv_hot;

        //商品的名称
        TextView tv_name;

        //商品的价格
        TextView tv_price;
    }

    /**自定义接口传递recycleview点击事件*/
    public interface OnHotGridView{

        /**
         * 当某个recycleview的item被点击的时候回调
         * @param position
         */
        public void onItemClick(int position);
    }

    /**供外界调用的 自定义点击事件接口 解决 GridView 在recycleview中 setOnItemClickListener不响应*/
    public void setOnHotGridView(OnHotGridView onHotGridView) {
        this.onHotGridView = onHotGridView;
    }
}
