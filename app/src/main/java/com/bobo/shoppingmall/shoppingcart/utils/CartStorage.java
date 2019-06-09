package com.bobo.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;

import com.bobo.shoppingmall.app.MyApplication;
import com.bobo.shoppingmall.home.bean.GoodsBean;
import com.bobo.shoppingmall.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 求知自学网 on 2019/6/2. Copyright © Leon. All rights reserved.
 * Functions:购物车存储器类 - 单例模式
 * 现在是将数据存在SharedPreferences中 阿福老师说最好存在SD卡中
 */
public class CartStorage {

    public static final String JSON_CART = "json_cart";

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

        //把list数据转换成SparseArray for in  的快捷键 iter
        for (int i = 0; i < goodsBeanList.size(); i++) {
            GoodsBean goodsBean = goodsBeanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    /**
     * 获取本地所有的数据
     * @return
     */
    public List<GoodsBean> getAllData() {

        List<GoodsBean> goodsBeanList = new ArrayList<>();

        //1.从本地获取
        String json = CacheUtils.getString(mContext,JSON_CART);

        //2.使用Gson转换成列表-判断json字符串是否为空 不为空的时候开始干活
        if (!TextUtils.isEmpty(json)){
            //把string类型转换成list
            goodsBeanList = new Gson().fromJson(json,new TypeToken<List<GoodsBean>>(){}.getType());
        }

        return goodsBeanList;
    }

    /**得到购物车实例  懒汉式*/
    public static CartStorage getInstance(){

        //这是阿福老师的写法没有规避线程安全风险
//        if (instance == null){
//            instance = new CartStorage(MyApplication.getContext());
//        }

        //懒汉式：在调用getInstance()方法后再去创建，先判断如果为空那就判断
        if (instance == null){
            //懒汉式有线程安全风险
            synchronized (CartStorage.class){
                if (instance == null){
                    instance = new CartStorage(MyApplication.getContext());
                }
            }
        }

        return instance;
    }

    /**
     * （用户往购物车中）添加数据
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean){

        //1.添加到内存中SparseArray 如果当前数据已经存在就修改number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));

        if (tempData != null){
            //内存中有了这条数据-购物车中已有了就+1
            tempData.setNumber(tempData.getNumber() + 1);
            //tempData.setName(tempData.getName() + 1); 之前这里写错找半天
        }else{
            //内存中没有这条数据-购物车中没有了就添加到购物车
            tempData = goodsBean;

            //避免出错还是给购物车的数量设置为1 bean类中默认为1设置后双重保险
            tempData.setNumber(1);
        }

        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);

        //2.同步到本地
        saveLocal();
    }

    /**
     * 删除购物车中的数据
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean){

        //1.内存中删除-以商品id作为key删除数据
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        //2.把内存中的数据保存到本地
        saveLocal();
    }

    /**
     * 修改(更新)购物车中的数据
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean){

        //1.内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);

        //2.同步到本地
        saveLocal();
    }

    /**
     * 将数据本地持久化保存-增删改查的时候都要同步持久化保存到本地
     */
    private void saveLocal(){

        //1.SparseArray转换成List
        List<GoodsBean> goodsBeanList = sparseToList();

        //2.使用Gson把列表(List)转换为string类型
        String json = new Gson().toJson(goodsBeanList);

        //3.把string做本地持久化保存
        CacheUtils.saveString(mContext,JSON_CART,json);
    }

    private List<GoodsBean> sparseToList(){
        List<GoodsBean> goodsBeanList = new ArrayList<>();

        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }

        return goodsBeanList;
    }
}
