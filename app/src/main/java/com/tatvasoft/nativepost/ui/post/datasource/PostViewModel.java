
package com.tatvasoft.nativepost.ui.post.datasource;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

public class PostViewModel extends ViewModel {
    public LiveData<PagedList<PostResponseModel.HitsItem>> userPagedList;

    public PostViewModel() {
        init();
    }
    private void init() {
        PostDataSourceFactory itemDataSourceFactory = new PostDataSourceFactory();
        LiveData<PostDataSource> liveDataSource = itemDataSourceFactory.userLiveDataSource;
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PostDataSource.PAGE_SIZE)
                .build();

        userPagedList = new LivePagedListBuilder<>(itemDataSourceFactory, config).build();
    }
}
