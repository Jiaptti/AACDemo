package com.aacdemo.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import butterknife.ButterKnife;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public abstract class BaseActivity <T extends ViewModel> extends AppCompatActivity implements LifecycleRegistryOwner {

    public T viewModel;

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public void getViewModel(Class<T> viewModelClass){
        viewModel =  ViewModelProviders.of(this).get(viewModelClass);
    }

    public abstract int getResourceId();

    public abstract void initData();

    public abstract void initView();
}
