package com.bobo.shoppingmall.utils;

/**
 * Created by 求知自学网 on 2019/5/12. Copyright © Leon. All rights reserved.
 * Functions: 配置联网地址 全局常量
 */
public class Constants {

    //基础路径
    public static final String BASE = "https://geekpark.site";
    // 请求Json数据基本URL
    public static final String BASE_URL_JSON = BASE+"/atguigu/json/";
    // 请求图片基本URL
    public static final String BASE_URl_IMAGE = BASE+"/atguigu/img";
    //小裙子
    public static final String SKIRT_URL = BASE_URL_JSON + "SKIRT_URL.json";
    //上衣
    public static final String JACKET_URL = BASE_URL_JSON + "JACKET_URL.json";
    //下装(裤子)
    public static final String PANTS_URL = BASE_URL_JSON + "PANTS_URL.json";
    //外套
    public static final String OVERCOAT_URL = BASE_URL_JSON + "OVERCOAT_URL.json";
    //配件
    public static final String ACCESSORY_URL = BASE_URL_JSON + "ACCESSORY_URL.json";
    //包包
    public static final String BAG_URL = BASE_URL_JSON + "BAG_URL.json";
    //装扮
    public static final String DRESS_UP_URL = BASE_URL_JSON + "DRESS_UP_URL.json";
    //居家宅品
    public static final String HOME_PRODUCTS_URL = BASE_URL_JSON + "HOME_PRODUCTS_URL.json";
    //办公文具
    public static final String STATIONERY_URL = BASE_URL_JSON + "STATIONERY_URL.json";
    //数码周边
    public static final String DIGIT_URL = BASE_URL_JSON +  "DIGIT_URL.json";
    //游戏专区
    public static final String GAME_URL = BASE_URL_JSON + "GAME_URL.json";

    //主页Fragment路径
    //public static final String HOME_URL = BASE_URL_JSON + "HOME_URL.json";

    //分类Fragment里面的标签Fragment页面数据
    public static final String TAG_URL = BASE_URL_JSON + "TAG_URL.json";


    public static final String NEW_POST_URL = BASE_URL_JSON + "NEW_POST_URL.json";
    public static final String HOT_POST_URL = BASE_URL_JSON + "HOT_POST_URL.json";

    //页面的具体数据的id
    public static final String GOODSINFO_URL = BASE_URL_JSON + "GOODSINFO_URL.json";

    //服饰
    public static final String CLOSE_STORE = BASE_URL_JSON + "CLOSE_STORE.json";
    //游戏
    public static final String GAME_STORE = BASE_URL_JSON + "GAME_STORE.json";
    //动漫
    public static final String COMIC_STORE = BASE_URL_JSON + "COMIC_STORE.json";
    //cosplay
    public static final String COSPLAY_STORE = BASE_URL_JSON + "COSPLAY_STORE.json";
    //古风
    public static final String GUFENG_STORE = BASE_URL_JSON + "GUFENG_STORE.json";
    //漫展
    public static final String STICK_STORE = BASE_URL_JSON + "STICK_STORE.json";
    //文具
    public static final String WENJU_STORE = BASE_URL_JSON + "WENJU_STORE.json";
    //零食
    public static final String FOOD_STORE = BASE_URL_JSON + "FOOD_STORE.json";
    //首饰厂
    public static final String SHOUSHI_STORE = BASE_URL_JSON + "SHOUSHI_STORE.json";


    public static Boolean isBackHome = false;

    //切换至分类要刷新数据的广播的key
    public static final String UPDATE_TYPE_DATA = "update_type_data";

    //要去购物车的广播key
    public static final String GOINGTOTHESHOPPINGCART = "goingToTheShoppingCart";


    //客服数据
    public static final String CALL_CENTER = "http://www6.53kf.com/webCompany.php?arg=10007" +
            "377&style=1&kflist=off&kf=info@atguigu.com,video@atguigu.com,public@atguigu.com," +
            "3069368606@qq.com,215648937@qq.com,sudan@atguigu.com,sszhang@atguigu.com&zdkf_type" +
            "=1&language=zh-cn&charset=gbk&referer=http%3A%2F%2Fwww.atguigu.com%2Fcontant.sht" +
            "ml&keyword=&tfrom=1&tpl=crystal_blue&timeStamp=1479001706368&ucust_id=";

    //----------------------------------------------------------------------------------------------


    /**网络请求的根目录*/
    public static final String BASE_URL = "https://geekpark.site/atguigu";

    /**主页面的路径*/
    public static final String HOME_URL =  BASE_URL+"/json/HOME_URL.json";

    /**图片的基本路径*/
    public static final String BASE_URL_IMAGE =  BASE_URL+"/img";

    /**(app版本)强制更新持久化保存的key*/
    public static final String MANDATORY_UPDATES = "mandatory_updates";

    /**(app版本)普通更新持久化保存的key*/
    public static final String UPDATES = "updates2019529";
}
