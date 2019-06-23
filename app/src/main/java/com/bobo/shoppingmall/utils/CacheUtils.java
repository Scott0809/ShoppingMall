package com.bobo.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Leon on 2019/1/1. Copyright © Leon. All rights reserved.
 * Functions: 數據持久化保存工具類
 */
public class CacheUtils {

    /**SharedPreferences（共享類） 的名稱*/
    private static final String SP_NAME = "CACHE";

    /**本地持久化存儲Boolean類型*/
    public static void setBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key,value).commit();
    }

    /**獲取本地持久化存儲的Boolean類型*/
    public static boolean getBoolean(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        boolean result = sp.getBoolean(key,false);
        return result;
    }

    /**本地持久化存儲String類型*/
    public static void saveString(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,value).commit();
    }

    /**獲取本地持久化存儲的String類型*/
    public static String getString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        String result = sp.getString(key,"");
        return result;
    }

    /**本地持久化存儲int類型*/
    public static void setInt(Context context,String key,int value){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key,value).commit();
    }

    /**獲取本地持久化存儲的Int類型*/
    public static int getInt(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        int result = sp.getInt(key,0);
        return result;
    }

    /**獲取本地持久化存儲的Int類型  有默认值*/
    public static int getIntHaveDefaultValue(Context context,String key,int defaultValue){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        int result = sp.getInt(key,defaultValue);
        return result;
    }

    /**本地持久化存儲Long類型*/
    public static void setLong(Context context,String key,long value){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        //谷歌建議使用apply() 和 commit() 效果一樣 apply是異步的方法
        edit.putLong(key,value).apply();
    }

    /**獲取本地持久化存儲的Long類型*/
    public static long getLong(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        long result = sp.getLong(key,0);
        return result;
    }

}