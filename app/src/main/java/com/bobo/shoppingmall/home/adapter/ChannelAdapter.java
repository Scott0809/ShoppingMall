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
import static com.bobo.shoppingmall.utils.Constants.BASE_URL_IMAGE;

import java.util.List;

/**
 * Created by 求知自学网 on 2019/5/18. Copyright © Leon. All rights reserved.
 * Functions: 首页fragment RecyclerView频道item的适配器
 */
public class ChannelAdapter extends BaseAdapter{


    private Context mContext;

    //数据源集合
    private List<ResultBeanData.ResultBean.ChannelInfoBean> datas;

    /**自定义点击事件的接口*/
    private OnChannelAdapter onChannelAdapter;


    public ChannelAdapter(Context context, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = context;
        this.datas = channel_info;
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
            convertView = View.inflate(mContext,R.layout.item_channel,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //根据位置得到对应的数据
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);

        //设置频道中的icon
        Glide.with(mContext).load(BASE_URL_IMAGE+channelInfoBean.getImage()).into(
                viewHolder.iv_icon);

        //设置频道的标题
        viewHolder.tv_title.setText(channelInfoBean.getChannel_name());

        //由于安卓4.4以上recycleview嵌套gridview 点击事件传递会有问题 自定义gridview的点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChannelAdapter != null){
                    onChannelAdapter.onItemClick(position);
                }
            }
        });

        return convertView;
    }

    //源代码中加了修饰符 static class ViewHolder
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
    }

    public interface OnChannelAdapter{
        void onItemClick(int position);
    }

    /**由于安卓4.4以上recycleview嵌套gridview 点击事件传递会有问题 自定义gridview的点击事件*/
    public void setOnChannelAdapter(OnChannelAdapter onChannelAdapter) {
        this.onChannelAdapter = onChannelAdapter;
    }
}
