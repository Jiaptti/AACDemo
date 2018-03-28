package com.aacdemo.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public abstract class BaseActivity <T extends ViewModel> extends FragmentActivity{
    public T viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        ButterKnife.bind(this);
        initData();
    }

    public void getViewModel(Class<T> viewModelClass){
        viewModel =  ViewModelProviders.of(this).get(viewModelClass);
    }

    public abstract int getResourceId();

    public abstract void initData();
}
