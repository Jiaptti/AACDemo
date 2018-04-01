package com.aacdemo.ui.main;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aacdemo.R;
import com.aacdemo.adapter.MainAdapter;
import com.aacdemo.api.ApiResponse;
import com.aacdemo.base.BaseActivity;
import com.aacdemo.base.BaseResult;
import com.aacdemo.entity.Location;
import com.aacdemo.entity.WeatherEntity;
import com.aacdemo.model.main.MainViewModel;
import com.aacdemo.persistence.entity.FavoriteEntity;
import com.aacdemo.ui.city.SearchActivity;
import com.aacdemo.view.MyClickListener;
import com.aacdemo.view.MyLongClickListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainViewModel> implements MyClickListener, MyLongClickListener {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fam)
    FloatingActionsMenu fam;
    @BindView(R.id.fab_region)
    FloatingActionButton region;
    @BindView(R.id.fab_search)
    FloatingActionButton search;

    private MainAdapter mAdapter;
    private List<WeatherEntity> mList = new ArrayList<>();
    private int mUpdateCount;

    @Override
    public void initView() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFavorite();
            }
        });
        mAdapter = new MainAdapter(this, mList);
        mAdapter.setHeaderRes(R.layout.item_header);
        mAdapter.setClickListener(this);
        mAdapter.setLongClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void loadFavorite(){
        mUpdateCount = 0;
        swipeRefreshLayout.setRefreshing(true);
        viewModel.loadFavorite().observe(this, new Observer<List<FavoriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntity> list) {
                if (list != null && list.size() > 0) {
                    mList.clear();
                    for (FavoriteEntity entity : list) {
                        WeatherEntity weatherNowEntity = new WeatherEntity(new Location(entity.id, entity.name, entity.path), null, "");
                        mList.add(weatherNowEntity);
                    }
                    mAdapter.notifyDataSetChanged();
                    loadWeather(list);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void loadWeather(List<FavoriteEntity> list){
        for (FavoriteEntity entity : list) {
            viewModel.getNowWeather(entity.id).observe(this, new Observer<ApiResponse<BaseResult<WeatherEntity>>>() {
                @Override
                public void onChanged(@Nullable ApiResponse<BaseResult<WeatherEntity>> baseWrapperEntityApiResponse) {
                    if (baseWrapperEntityApiResponse != null && baseWrapperEntityApiResponse.isSuccess()) {
                        BaseResult<WeatherEntity> wrapperEntity = baseWrapperEntityApiResponse.body;
                        if (wrapperEntity != null) {
                            WeatherEntity weatherNowEntity = wrapperEntity.getResults().get(0);
                            updateWeather(weatherNowEntity);
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }
    }

    private void updateWeather(WeatherEntity weatherNowEntity){
        for (WeatherEntity nowEntity : mList) {
            if (nowEntity.getLocation().getId().equals(weatherNowEntity.getLocation().getId())) {
                nowEntity.setNow(weatherNowEntity.getNow());
                nowEntity.setLast_update(weatherNowEntity.getLast_update());
                ++mUpdateCount;
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
        if (mUpdateCount == mList.size()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        getViewModel(MainViewModel.class);
        loadFavorite();
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @OnClick({R.id.fab_search, R.id.fab_region})
    public void onClick(View view) {
        fam.collapse();
        if (view.getId() == R.id.fab_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.fab_region) {
//            Intent intent = new Intent(this, RegionActivity.class);
//            startActivity(intent);
        }
    }
}
