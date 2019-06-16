package com.bobo.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.home.bean.ResultBeanData;
import com.bumptech.glide.Glide;

import java.util.List;
import static com.bobo.shoppingmall.utils.Constants.BASE_URL_IMAGE;

/**
 * Created by 求知自学网 on 2019/5/25. Copyright © Leon. All rights reserved.
 * Functions: 推荐部分 的 GridView 适配器
 */
public class RecommendGridViewAdapter extends BaseAdapter {

    private Context mConext;
    private List<ResultBeanData.ResultBean.RecommendInfoBean> datas;

    //传递点击事件的接口
    private OnRecommendGridView onRecommendGridView;

    public RecommendGridViewAdapter(Context mConext, List<ResultBeanData.ResultBean.RecommendInfoBean>
            recommend_info) {
        this.mConext = mConext;
        this.datas = recommend_info;
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
            convertView = View.inflate(mConext, R.layout.item_recommend_grid_view,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_recommend = (ImageView)convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            //将viewHolder设置给convertView,绑定起来
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到对应数据
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = datas.get(position);
        Glide.with(mConext).load(BASE_URL_IMAGE+recommendInfoBean.getFigure()).placeholder(R.
                drawable.top_btn).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("￥"+recommendInfoBean.getCover_price());

        //由于gridview自带的点击事件嵌套在recycleview中不能用 自定义点击事件的监听
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecommendGridView != null){
                    onRecommendGridView.onItemClick(position);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        //商品封面图
        private ImageView iv_recommend;
        //商品名称
        private TextView tv_name;
        //商品价格
        private TextView tv_price;
    }

    public interface OnRecommendGridView{
        void onItemClick(int position);
    }

    /**GridView自带的点击事件嵌套在recycleView中不能使用 自定义了点击事件的接口*/
    public void setOnRecommendGridView(OnRecommendGridView onRecommendGridView) {
        this.onRecommendGridView = onRecommendGridView;
    }
}
