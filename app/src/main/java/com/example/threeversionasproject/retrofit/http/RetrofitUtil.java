package com.example.threeversionasproject.retrofit.http;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */

public class RetrofitUtil {
    private static volatile RetrofitUtil Instance;

    private Retrofit mRetrofit;
    private HttpRequest mHttpRequest;

    private RetrofitUtil() {
        mRetrofit = new Retrofit.Builder().client(OkhttpUtils.getInstance())
                .baseUrl("")
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mHttpRequest = mRetrofit.create(HttpRequest.class);
    }

    public static RetrofitUtil getInstance() {
        if (null == Instance) {
            synchronized (RetrofitUtil.class) {
                if (null == Instance) {
                    Instance = new RetrofitUtil();
                }
            }
        }
        return Instance;
    }

    public HttpRequest getHttpRequest() {
        return mHttpRequest;
    }
}
