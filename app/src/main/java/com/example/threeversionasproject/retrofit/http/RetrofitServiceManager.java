package com.example.threeversionasproject.retrofit.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ocean on 2020/6/7.
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_READ_OUT = 10;

    private Retrofit mRetrofit;

    private HttpRequest httpRequest;

    public RetrofitServiceManager() {
        //创建okhttpclient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_OUT, TimeUnit.SECONDS);

//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("key", "value")
//                .addHeaderParams("key", "value")
//                .addHeaderParams("key", "value")
//                .build();
//        builder.addInterceptor(commonInterceptor);

        //创建retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://v.juhe.cn")
                .build();

        httpRequest=create(HttpRequest.class);
    }

    private static class SingleHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitServiceManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}
