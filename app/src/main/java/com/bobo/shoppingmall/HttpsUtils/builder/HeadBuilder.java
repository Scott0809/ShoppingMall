package com.bobo.shoppingmall.HttpsUtils.builder;


import com.bobo.shoppingmall.HttpsUtils.OkHttpUtils;
import com.bobo.shoppingmall.HttpsUtils.request.OtherRequest;
import com.bobo.shoppingmall.HttpsUtils.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
