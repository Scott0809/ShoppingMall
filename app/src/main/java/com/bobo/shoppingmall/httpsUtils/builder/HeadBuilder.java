package com.bobo.shoppingmall.httpsUtils.builder;


import com.bobo.shoppingmall.httpsUtils.OkHttpUtils;
import com.bobo.shoppingmall.httpsUtils.request.OtherRequest;
import com.bobo.shoppingmall.httpsUtils.request.RequestCall;

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
