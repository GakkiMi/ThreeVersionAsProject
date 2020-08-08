package com.example.threeversionasproject.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.threeversionasproject.R;

import com.example.threeversionasproject.retrofit.http.DialogObserverImp;
import com.example.threeversionasproject.retrofit.http.HttpClient;
import com.example.threeversionasproject.retrofit.http.MyObserver;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitActivity extends AppCompatActivity {


    public static final String BASE_URL = "http://v.juhe.cn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        requestGet();
        request();
        List<Object> mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Object obj = new Object();
            mList.add(obj);
            obj = null;
        }


        try {
            String s =  encodeBase64File("/storage/emulated/0/ECGDATA/JPEG/-20200610094144.jpeg");
            Log.i("","------s:"+s);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
        * 将文件转成base64 字符串
        *
        * @param path文件路径
        * @return *
        * @throws Exception
        */
    public  String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();

        return Base64Utils.encode(buffer);
    }


    private void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //retrofit原生请求
        MovieService movieService = retrofit.create(MovieService.class);
        Call<Object> call = movieService.post("top", "c46099d74c3af1291671b371ebe85ba5");
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.i("", "----------------retrofit:成功");
                Object resultStr = response.body();
                Log.i("", "----------------retrofit:" + resultStr);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.i("", "----------------retrofit:失败");
                t.printStackTrace();
            }
        });


        //retrofit +rxjava 请求
        MovieService service = retrofit.create(MovieService.class);
        Observable observable = service.get("top", "c46099d74c3af1291671b371ebe85ba5");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Log.i("", "----------------retrofit:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void requestGet() {

        //retrofit +rxjava +okhttp封装请求
        HttpClient.Login("top", "c46099d74c3af1291671b371ebe85ba5", new MyObserver(new DialogObserverImp() {
            @Override
            public void onNext(Object value) {
                Log.i("", "----------------retrofit:" + value);
            }
        }));
    }

}
