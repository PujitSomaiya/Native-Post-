package com.tatvasoft.nativepost.interfaces;

import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestApi {
//    @GET("api/v1/search_by_date?tags=story&page=")
//    Observable<PostResponseModel> getAllPost(
//            @Query("page") int pageIndex
//    );

    @GET("api/v1/search_by_date?tags=story&page=")
    Call<PostResponseModel> getPost(@Query("page") Long pageIndex);
}
