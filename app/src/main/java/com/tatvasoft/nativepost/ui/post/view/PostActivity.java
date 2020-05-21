package com.tatvasoft.nativepost.ui.post.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.databinding.ActivityPostBinding;
import com.tatvasoft.nativepost.interfaces.RequestApi;
import com.tatvasoft.nativepost.netowrk.RetrofitClient;
import com.tatvasoft.nativepost.ui.post.adapter.PostRecyclerAdapter;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PostActivity extends AppCompatActivity {

    private ActivityPostBinding binding;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RequestApi requestApi;
    private Observable<PostResponseModel> taskObservable;
    private PostRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        initControls();
    }

    private void initControls() {
        initListeners();
        fetchData();
    }

    private void fetchData() {
        taskObservable= requestApi.getAllPost();
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
                        displayData(getList(hitsItems));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "onComplete: called.");
                    }
                });
    }

    private List<PostResponseModel.HitsItem> getList(PostResponseModel postResponseModel){
        List<PostResponseModel.HitsItem> hitsItems=new ArrayList<>();
        for (int i=1;i<postResponseModel.getHits().size();i++){
            hitsItems.add(postResponseModel.getHits().get(i));
        }
        return hitsItems;
    }


    private void displayData(List<PostResponseModel.HitsItem> posts) {
        adapter = new PostRecyclerAdapter(this, posts);
        binding.recyclerPosts.setAdapter(adapter);
    }

    private void initListeners() {
        Retrofit retrofit = RetrofitClient.getInstance();
        requestApi = retrofit.create(RequestApi.class);
        binding.recyclerPosts.setHasFixedSize(true);
        binding.recyclerPosts.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


}
