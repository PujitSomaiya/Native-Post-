package com.tatvasoft.nativepost.interfaces;

import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RequestApi {
    @GET("/api/v1/search_by_date?tags=story&page=1")
    Observable<PostResponseModel> getAllPost();
}
