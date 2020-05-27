package com.tatvasoft.nativepost.ui.post.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tatvasoft.nativepost.R;
import com.tatvasoft.nativepost.databinding.ActivityPostBinding;
import com.tatvasoft.nativepost.interfaces.RecyclerInterface;
import com.tatvasoft.nativepost.ui.post.adapter.PostAdapter;
import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;
import com.tatvasoft.nativepost.ui.post.datasource.PostViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class PostActivity extends AppCompatActivity implements View.OnClickListener, RecyclerInterface, LifecycleOwner {

    private ActivityPostBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isExit = false;
    private TextView tvCheckNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        initControls();
        fetchData();
    }

    private void fetchData() {
        final PostAdapter adapter = new PostAdapter(this);
        binding.recyclerPosts.setLayoutManager(new LinearLayoutManager(this));
        PostViewModel itemViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        itemViewModel.userPagedList.observe(this, adapter::submitList);
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (itemViewModel.userPagedList.getValue().get(i).isCheck()) {
                itemViewModel.userPagedList.getValue().get(i).setCheck(false);
            }
        }
        binding.recyclerPosts.setAdapter(adapter);
    }

    private void initControls() {
        initListeners();
        refreshLayout();
    }


    private void refreshLayout() {
        binding.swipeToRefresh.setColorSchemeResources(R.color.colorAccent);
        binding.swipeToRefresh.setOnRefreshListener(() -> {
            fetchData();
            onItemClick(0);
            binding.swipeToRefresh.setRefreshing(false);
        });
    }

    private List<PostResponseModel.HitsItem> getList(PostResponseModel postResponseModel) {
        List<PostResponseModel.HitsItem> hitsItems = new ArrayList<>();
        for (int i = 1; i < postResponseModel.getHits().size(); i++) {
            hitsItems.add(postResponseModel.getHits().get(i));
        }
        return hitsItems;
    }

    private void initListeners() {
        binding.recyclerPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerPosts.setLayoutManager(linearLayoutManager);
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
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            super.onBackPressed();
        }
        isExit = true;
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
