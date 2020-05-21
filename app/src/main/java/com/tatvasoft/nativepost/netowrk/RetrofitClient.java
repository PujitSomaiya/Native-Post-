package com.tatvasoft.nativepost.netowrk;

import com.tatvasoft.nativepost.interfaces.RequestApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient ourInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://hn.algolia.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
//    Retrofit getInstance() {
//        if (ourInstance == null)
//            ourInstance = new Retrofit.Builder()
//                        .baseUrl("https://hn.algolia.com")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//        return ourInstance;
//    }

    public static synchronized RetrofitClient getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitClient();
        }
        return ourInstance;
    }

    public RequestApi getApi() {
        return retrofit.create(RequestApi.class);
    }
}
