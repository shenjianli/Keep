package com.shen.keep.core.api;


import com.shen.keep.core.HttpResult;
import com.shen.keep.model.Quote;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by shenjianlis on 2016/8/31.
 * 测试网络请求的接口
 */
public interface QuoteApi {

    //请求的url地址
    @GET("quote/query")
    Observable<HttpResult<Quote>> queryQuoteInfo();

}
