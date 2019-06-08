package com.bobo.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * Created by 求知自学网 on 2019/6/2. Copyright © Leon. All rights reserved.
 * Functions: 商品对象模型 因为要传递 必须序列化  implements Serializable
 * 注意本项目使用的是:阿里公司开源的 Fastjson
 */
public class GoodsBean implements Serializable {

    /**商品的价格*/
    private String cover_price;

    /**商品图片相对路径*/
    private String figure;

    /**商品名*/
    private String name;

    /**产品id*/
    private String product_id;

    /**用户添加到购物车中的商品个数 默认为1*/
    private int number = 1;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                '}';
    }
}
