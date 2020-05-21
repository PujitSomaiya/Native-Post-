package com.tatvasoft.nativepost.ui.post.model;


import android.content.ClipData;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.tatvasoft.nativepost.netowrk.RetrofitClient;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, PostResponseModel> {

    //the size of a page that we want
    public static final int PAGE_SIZE = 50;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    //we need to fetch from stackoverflow
    private static final String SITE_NAME = "stackoverflow";


    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, PostResponseModel> callback) {
        RetrofitClient.getInstance()
                .getApi().getAllPost(FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostResponseModel postResponseModel) {
                        callback.onResult((List<PostResponseModel>) postResponseModel, null, FIRST_PAGE + 1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, PostResponseModel> callback) {
        RetrofitClient.getInstance()
                .getApi().getAllPost(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostResponseModel postResponseModel) {
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (postResponseModel != null) {
                            callback.onResult((List<PostResponseModel>) postResponseModel, adjacentKey);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PostResponseModel> callback) {
        RetrofitClient.getInstance()
                .getApi().getAllPost(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostResponseModel postResponseModel) {
                        if (postResponseModel != null) {
                            //if the response has next page
                            //incrementing the next page number
                            Integer key = postResponseModel.isExhaustiveNbHits() ? params.key + 1 : null;

                            //passing the loaded data and next page value
                            callback.onResult( postResponseModel, key);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
