package com.bobo.shoppingmall.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bobo.shoppingmall.R;
import com.bobo.shoppingmall.home.bean.ResultBeanData;
import com.bobo.shoppingmall.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;
import static com.bobo.shoppingmall.utils.Constants.BASE_URL_IMAGE;

/**
 * Created by 求知自学网 on 2019/5/25. Copyright © Leon. All rights reserved.
 * Functions: (横向滚动)秒杀的recycleview 的适配器
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHodler> {

    private Context mContext;

    private OnSeckillRecycleView onSeckillRecycleView;

    //数据源集合
    private List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean
            .ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itenView = View.inflate(mContext, R.layout.item_sekill,null);
        return new ViewHodler(itenView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler hodler, int position) {
        //1.根据位置得到对应的数据
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);

        //2.绑定数据
        Glide.with(mContext).load(BASE_URL_IMAGE+listBean.getFigure()).placeholder(R.drawable.
                top_btn).into(hodler.iv_figure);
        hodler.tv_cover_price.setText(listBean.getCover_price());
        hodler.tv_origin_price.setText(listBean.getOrigin_price());


        //动态计算 原价上的中划线的最佳宽度
        String comingStr = listBean.getOrigin_price() + "占位";
        Paint pFont = new Paint();
        Rect rect = new Rect();
        pFont.getTextBounds(comingStr, 0, comingStr.length() - 1, rect);

        //给原价上的中划线设置宽度
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) hodler.middleLine.getLayoutParams();
        params.width = DensityUtil.dip2px(mContext,(float)rect.width());
        hodler.middleLine.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder{

        private ImageView iv_figure;

        /**售价*/
        private TextView tv_cover_price;

        /**原价*/
        private TextView tv_origin_price;

        /**原价下面的中划线*/
        private View middleLine;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            iv_figure = (ImageView)itemView.findViewById(R.id.iv_figure);
            tv_cover_price = (TextView)itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView)itemView.findViewById(R.id.tv_origin_price);
            middleLine = (View)itemView.findViewById(R.id.v_middle_line);

            //设置点击事件的监听
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"秒杀="+,Toast.LENGTH_SHORT).show();
                    if (onSeckillRecycleView != null){
                        onSeckillRecycleView.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**自定义接口传递recycleview点击事件*/
    public interface OnSeckillRecycleView{

        /**
         * 当某个recycleview的item被点击的时候回调
         * @param position
         */
        public void onItemClick(int position);
    }

    /**
     * 设置item的监听
     * @param onSeckillRecycleView
     */
    public void setOnSeckillRecycleView(OnSeckillRecycleView onSeckillRecycleView) {
        this.onSeckillRecycleView = onSeckillRecycleView;
    }
}

