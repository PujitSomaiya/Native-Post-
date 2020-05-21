package com.tatvasoft.nativepost.ui.post.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tatvasoft.nativepost.interfaces.RequestApi;
import com.tatvasoft.nativepost.netowrk.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainViewModel extends AndroidViewModel {
    public MutableLiveData<PostResponseModel> _userPostDetailsModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> _errorLiveData = new MutableLiveData<>();
    private PostRepository postRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(getApplication());
    }

//    public void getPostData() {
//        Observable<PostResponseModel> taskObservable = postRepository.getPostDetails();
//        taskObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map((PostResponseModel postResponseModels) -> postResponseModels)
//                .subscribe(new Observer<PostResponseModel>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        compositeDisposable.add(d);
//                    }
//
//                    @Override
//                    public void onNext(PostResponseModel hitsItems) {
//                        _userPostDetailsModelMutableLiveData.postValue(hitsItems);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("TAG", "onError: " + e.getMessage());
//                        _errorLiveData.postValue(e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("TAG", "onComplete: called.");
//                    }
//                });
//    }


    public void loadNextPage(int pageNumber) {
        Retrofit retrofit = RetrofitClient.getInstance();
        RequestApi requestApi = retrofit.create(RequestApi.class);
        Observable<PostResponseModel> taskObservable = requestApi.getAllPost(pageNumber);
        taskObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((PostResponseModel postResponseModels) -> postResponseModels)
                .subscribe(new Observer<PostResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PostResponseModel hitsItems) {
                        _userPostDetailsModelMutableLiveData.postValue(hitsItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onError: " + e.getMessage());
                        _errorLiveData.postValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "onComplete: called.");
                    }
                });
    }


}
