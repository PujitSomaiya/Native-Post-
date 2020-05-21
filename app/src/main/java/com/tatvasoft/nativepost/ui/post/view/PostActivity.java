package com.tatvasoft.nativepost.ui.post.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.databinding.ActivityPostBinding;
import com.tatvasoft.nativepost.interfaces.RecyclerInterface;
import com.tatvasoft.nativepost.ui.post.adapter.PostRecyclerAdapter;
import com.tatvasoft.nativepost.ui.post.model.MainViewModel;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class PostActivity extends AppCompatActivity implements View.OnClickListener, RecyclerInterface {

    private ActivityPostBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainViewModel mainViewModel;
    private int pageNumber=1;
    private boolean isExit=false;
    private Toolbar toolbar;
    private TextView tvCheckNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        initControls();
    }

    private void initControls() {
        initListeners();
        fetchData();
        refreshLayout();
    }

    private void refreshLayout() {
        binding.swipeToRefresh.setColorSchemeResources(R.color.colorAccent);
        binding.swipeToRefresh.setOnRefreshListener(() -> {
            fetchData();
            binding.swipeToRefresh.setRefreshing(false);
        });
    }

    private void fetchData() {
        mainViewModel._userPostDetailsModelMutableLiveData.observe(this, postResponseModel -> displayData(postResponseModel,getList(postResponseModel)));
        mainViewModel._errorLiveData.observe(this, s -> Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show());
    }


    private List<PostResponseModel.HitsItem> getList(PostResponseModel postResponseModel){
        List<PostResponseModel.HitsItem> hitsItems=new ArrayList<>();
        for (int i=1;i<postResponseModel.getHits().size();i++){
            hitsItems.add(postResponseModel.getHits().get(i));
        }
        return hitsItems;
    }


    private void displayData(PostResponseModel posts,List<PostResponseModel.HitsItem> postsHits) {
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(this, posts,postsHits,this);
        binding.recyclerPosts.setAdapter(adapter);
    }

    private void initListeners() {
        binding.recyclerPosts.setHasFixedSize(true);
        binding.recyclerPosts.setLayoutManager(new LinearLayoutManager(this));
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.loadNextPage(pageNumber);
        binding.btnNext.setOnClickListener(this);
        toolbar=findViewById(R.id.toolbar);
       tvCheckNumber=toolbar.findViewById(R.id.tvCheckNumber);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnNext){
            pageNumber++;
            mainViewModel.loadNextPage(pageNumber);
            fetchData();
            isExit=false;
        }
    }

    @Override
    public void onBackPressed() {
        if (pageNumber==1){
            if (isExit){
                super.onBackPressed();
            }
            Toast.makeText(getApplicationContext(),"No back page",Toast.LENGTH_SHORT).show();
            isExit=true;
        }else {
            pageNumber--;
            mainViewModel.loadNextPage(pageNumber);
            fetchData();
            isExit=false;
        }
    }

    @Override
    public void onItemClick(int number) {

            tvCheckNumber.setText(String.valueOf(number));

    }
}
