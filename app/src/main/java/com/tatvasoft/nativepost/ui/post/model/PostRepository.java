package com.tatvasoft.nativepost.ui.post.model;

import android.app.Application;

import com.tatvasoft.nativepost.interfaces.RequestApi;
import com.tatvasoft.nativepost.netowrk.RetrofitClient;
import io.reactivex.Observable;
import retrofit2.Retrofit;

public class PostRepository {

    PostRepository(Application application) {

    }

//   public Observable<PostResponseModel> getPostDetails() {
//        Retrofit retrofit = RetrofitClient.getInstance();
//        RequestApi requestApi = retrofit.create(RequestApi.class);
//        return requestApi.getAllPost(1);
//    }

}
