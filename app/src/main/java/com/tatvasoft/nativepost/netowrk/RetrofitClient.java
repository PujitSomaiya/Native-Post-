package com.tatvasoft.nativepost.netowrk;

import com.tatvasoft.nativepost.interfaces.RequestApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    private static Retrofit ourInstance;
//
//    public static Retrofit getInstance() {
//        if (ourInstance == null)
//            ourInstance = new Retrofit.Builder()
//                    .baseUrl("https://hn.algolia.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//        return ourInstance;
//    }

    // Base URL
    private static final String URL = "https://hn.algolia.com/";
    private static HttpLoggingInterceptor logger =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder().addInterceptor(logger);
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build());
    private static Retrofit retrofit = builder.build();
    public static <T> T buildService(Class<T> type) {
        return retrofit.create(type);
    }
}
