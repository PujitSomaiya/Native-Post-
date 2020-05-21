package com.tatvasoft.nativepost.ui.post.model;

import android.content.ClipData;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ItemDataSourceFactory extends DataSource.Factory<Integer, PostResponseModel> {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, PostResponseModel>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, PostResponseModel> create() {
        //getting our data source object
        ItemDataSource itemDataSource = new ItemDataSource();

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource);

        //returning the datasource
        return itemDataSource;
    }


    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, PostResponseModel>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}