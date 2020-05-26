package com.tatvasoft.nativepost.ui.post.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.tatvasoft.nativepost.ui.post.model.PostResponseModel;

public class PostDataSourceFactory extends DataSource.Factory<Long, PostResponseModel.HitsItem> {
    public MutableLiveData<PostDataSource> userLiveDataSource=new MutableLiveData<>();
    @Override public DataSource<Long, PostResponseModel.HitsItem> create() {
        PostDataSource userDataSource = new PostDataSource();
        userLiveDataSource.postValue(userDataSource);
        return userDataSource;
    }
}
