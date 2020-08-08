package com.example.threeversionasproject.retrofit.http;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ocean on 2020/6/8.
 */

public interface HttpRequest {


    //聚合数据  头条新闻
    @GET("/toutiao/index")
    Observable<Object> get(@Query("type") String type, @Query("key") String key);



}
