package com.example.threeversionasproject.retrofit.http;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ocean on 2020/6/8.
 */

public class HttpClient {


    private static HttpRequest mHttpRequest = RetrofitServiceManager.getInstance().getHttpRequest();


    public HttpClient() {
    }

    /**
     * 封装线程管理和订阅的过程
     */
    private static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public static void Login(String type, String key, Observer observer) {
        ApiSubscribe(mHttpRequest.get(type, key), observer);
    }




}
