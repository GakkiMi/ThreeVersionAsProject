package com.example.threeversionasproject.retrofit.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ocean on 2020/6/8.
 */

public class MyObserver<T> implements Observer<T> {

    DialogObserverImp imp;

    public MyObserver(DialogObserverImp imp) {
        this.imp = imp;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {
        imp.onNext(value);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }



}
