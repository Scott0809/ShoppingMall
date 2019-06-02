package com.bobo.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.util.SparseArray;

import com.bobo.shoppingmall.app.MyApplication;
import com.bobo.shoppingmall.home.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 求知自学网 on 2019/6/2. Copyright © Leon. All rights reserved.
 * Functions:购物车存储器类 - 单例模式
 */
public class CartStorage {

    private static CartStorage instance;

    private Context mContext;

    //存储购物车数据的集合 SparseArray去替换HashMap的使用 性能优于后者
    private SparseArray<GoodsBean> sparseArray;

    /**私有的构造方法单例标配*/
    private CartStorage(Context context){
        this.mContext = context;

        //把之前存如的数据读取出来 initalCapacity 自然容量
        sparseArray = new SparseArray<>(100);

        listToSparseArray();
    }

    /**从本地读取的数据加入到SparseArray中*/
    private void listToSparseArray(){
        List<GoodsBean> goodsBeanList = getAllData();
    }

    /**
     * 获取本地所有的数据
     * @return
     */
    public List<GoodsBean> getAllData() {

        //1.从本地获取
       // String json = CacheUtils.getString(mContext,"json_cart");

        //2.使用Gson转换成列表

        return null;
    }

    /**得到购物车实例*/
    public static CartStorage getInstance(){

        if (instance == null){
            instance = new CartStorage(MyApplication.getContext());
        }

        return instance;
    }
}
