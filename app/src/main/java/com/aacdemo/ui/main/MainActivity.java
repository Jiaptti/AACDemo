package com.aacdemo.ui.main;

import com.aacdemo.R;
import com.aacdemo.base.BaseActivity;
import com.aacdemo.model.main.MainViewModel;

public class MainActivity extends BaseActivity<MainViewModel> {


    @Override
    public int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        getViewModel(MainViewModel.class);
    }
}
