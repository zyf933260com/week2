package com.example.zk3.util;

import com.example.zk3.api.Api;
import com.example.zk3.api.ApiService;
import com.example.zk3.net.HeaderInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetfroUtils {

    private static volatile RetfroUtils instence;
    private final OkHttpClient okHttpClient;
    private final ApiService apiService;

    //单例模式
    public static RetfroUtils getInstence() {
        if (instence==null){
            synchronized (RetfroUtils.class){
                if (instence==null){
                    instence=new RetfroUtils();
                }
            }
        }
        return instence;
    }
    //构造方法
    private RetfroUtils(){
        //日志拦截器
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //初始化okHttpClient
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HeaderInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(interceptor)
                .readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        //初始化Retrofit
        Retrofit   retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Api.PATH)
                .client(okHttpClient)
                .build();
        apiService = retrofit.create(ApiService.class);


    }
    //get方法
    public void doGet(String url, HashMap<String,String> map,final RetroCallBack callBack){
        Observer  observer = new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (callBack!=null){
                    try {
                        callBack.success(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
             if (callBack!=null){
                 callBack.failure(e.getMessage());
             }
            }

            @Override
            public void onComplete() {

            }
        };


        apiService.get(url, map)
            .subscribeOn(Schedulers.io())   //io子线程
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer);

    }



}
