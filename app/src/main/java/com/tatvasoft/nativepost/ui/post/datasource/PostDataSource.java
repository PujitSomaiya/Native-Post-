
package com.tatvasoft.nativepost.ui.post.datasource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.tatvasoft.nativepost.interfaces.RequestApi;
import com.tatvasoft.nativepost.netowrk.RetrofitClient;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<Long, PostResponseModel.HitsItem> {
    public static int PAGE_SIZE = 6;
    public static long FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, PostResponseModel.HitsItem> callback) {
        RequestApi service = RetrofitClient.buildService(RequestApi.class);
        Call<PostResponseModel> call = service.getPost(FIRST_PAGE);
        call.enqueue(new Callback<PostResponseModel>() {
            @Override
            public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                PostResponseModel apiResponse = response.body();
                if (apiResponse != null) {
                    List<PostResponseModel.HitsItem> responseItems = apiResponse.getHits();
                    callback.onResult(responseItems, null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<PostResponseModel> call, Throwable t) {
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Long> params,
                           @NonNull final LoadCallback<Long, PostResponseModel.HitsItem> callback) {
        RequestApi service = RetrofitClient.buildService(RequestApi.class);
        Call<PostResponseModel> call = service.getPost(params.key);
        call.enqueue(new Callback<PostResponseModel>() {
            @Override
            public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                PostResponseModel apiResponse = response.body();
                if (apiResponse != null) {
                    List<PostResponseModel.HitsItem> responseItems = apiResponse.getHits();
                    long key;
                    if (params.key > 1) {
                        key = params.key - 1;
                    } else {
                        key = 0;
                    }
                    callback.onResult(responseItems, key);
                }
            }

            @Override
            public void onFailure(Call<PostResponseModel> call, Throwable t) {
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long,PostResponseModel.HitsItem> callback) {
        RequestApi service = RetrofitClient.buildService(RequestApi.class);
        Call<PostResponseModel> call = service.getPost(params.key);
        call.enqueue(new Callback<PostResponseModel>() {
            @Override
            public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                PostResponseModel apiResponse = response.body();
                if (apiResponse != null) {
                    List<PostResponseModel.HitsItem> responseItems = apiResponse.getHits();
                    callback.onResult(responseItems, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<PostResponseModel> call, Throwable t) {
            }
        });
    }
}
