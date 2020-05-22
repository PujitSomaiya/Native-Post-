package com.tatvasoft.nativepost.ui.post.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class PostActivity extends AppCompatActivity implements View.OnClickListener, RecyclerInterface, LifecycleOwner {

    private ActivityPostBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainViewModel mainViewModel;
    private int pageNumber = 1, pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean isExit = false;
    private TextView tvCheckNumber;
    private boolean loading = true;
    private LinearLayoutManager linearLayoutManager;
    private  PostRecyclerAdapter adapter;
    private Context mContext=this;

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
        endlessScroll();
    }

    private void endlessScroll() {
        binding.recyclerPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            pageNumber++;
                            mainViewModel.loadNextPage(pageNumber);
                            mainViewModel._userPostDetailsModelMutableLiveData.observe((LifecycleOwner) mContext, postResponseModel -> adapter.updateData(getList(postResponseModel)));
                        }
                    }
                }
            }
        });
    }

    private void refreshLayout() {
        binding.swipeToRefresh.setColorSchemeResources(R.color.colorAccent);
        binding.swipeToRefresh.setOnRefreshListener(() -> {
            fetchData();
            onItemClick(0);
            binding.swipeToRefresh.setRefreshing(false);
        });
    }

    private void fetchData() {
        mainViewModel._userPostDetailsModelMutableLiveData.observe(this, postResponseModel -> displayData(postResponseModel, getList(postResponseModel)));
        mainViewModel._errorLiveData.observe(this, s -> Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show());
    }


    private List<PostResponseModel.HitsItem> getList(PostResponseModel postResponseModel) {
        List<PostResponseModel.HitsItem> hitsItems = new ArrayList<>();
        for (int i = 1; i < postResponseModel.getHits().size(); i++) {
            hitsItems.add(postResponseModel.getHits().get(i));
        }
        return hitsItems;
    }


    private void displayData(PostResponseModel posts, List<PostResponseModel.HitsItem> postsHits) {
         adapter = new PostRecyclerAdapter(this, posts, postsHits, this);
        binding.recyclerPosts.setAdapter(adapter);
    }

    private void initListeners() {
        binding.recyclerPosts.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerPosts.setLayoutManager(linearLayoutManager);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.loadNextPage(pageNumber);
        binding.btnNext.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvCheckNumber = toolbar.findViewById(R.id.tvCheckNumber);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNext) {
            pageNumber++;
            mainViewModel.loadNextPage(pageNumber);
            fetchData();
            isExit = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (pageNumber == 1) {
            if (isExit) {
                super.onBackPressed();
            }
            Toast.makeText(getApplicationContext(), "No back page", Toast.LENGTH_SHORT).show();
            isExit = true;
        } else {
            pageNumber--;
            mainViewModel.loadNextPage(pageNumber);
            fetchData();
            isExit = false;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(int number) {
        if (number != 0) {
            tvCheckNumber.setText("Selected: " + number);
        } else {
            tvCheckNumber.setText("");
        }

    }
}
