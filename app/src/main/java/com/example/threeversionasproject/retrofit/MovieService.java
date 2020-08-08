package com.example.threeversionasproject.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ocean on 2020/6/7.
 */

public interface MovieService {

    //聚合数据  头条新闻
    @GET("/toutiao/index")
    Observable<Object> get(@Query("type") String type, @Query("key") String key);


    //聚合数据  头条新闻
    @FormUrlEncoded
    @POST("/toutiao/index")
    Call<Object> post(@Field("type") String type, @Field("key") String key);
}
