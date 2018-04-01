package com.aacdemo.ui.city;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aacdemo.R;
import com.aacdemo.adapter.SearchAdapter;
import com.aacdemo.api.ApiResponse;
import com.aacdemo.base.BaseActivity;
import com.aacdemo.base.BaseResult;
import com.aacdemo.entity.Location;
import com.aacdemo.model.search.SearchViewModel;
import com.aacdemo.persistence.entity.FavoriteEntity;
import com.aacdemo.repository.FavoriteRepository;
import com.aacdemo.utils.ToastUtils;
import com.aacdemo.view.SearchClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/30.
 */

public class SearchActivity extends BaseActivity<SearchViewModel> implements SearchClickListener{
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.search_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SearchAdapter mAdapter;
    private List<Location> mList = new ArrayList<>();
    private List<FavoriteEntity> favoriteList = new ArrayList<>();

    @Override
    public int getResourceId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        getViewModel(SearchViewModel.class);
        viewModel.loadFavorites().observe(this, new Observer<List<FavoriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntity> favoriteEntities) {
                favoriteList.addAll(favoriteEntities);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCity(newText);
                return false;
            }
        });
        return true;
    }

    private void searchCity(String q){
        if(TextUtils.isEmpty(q.trim())) return ;
        showLoading();
        viewModel.searchCity(q).observe(this, new Observer<ApiResponse<BaseResult<Location>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<BaseResult<Location>> baseResultApiResponse) {
                mList.clear();
                if(baseResultApiResponse != null && baseResultApiResponse.isSuccess()){
                    BaseResult<Location> baseResult = baseResultApiResponse.body;
                    if(baseResult != null){
                        List<Location> list = baseResult.getResults();
                        if(list != null && list.size() > 0){
                            setFavoriteImg(list);
                        } else {
                            ToastUtils.show(SearchActivity.this, R.string.search_city_failed);
                        }
                    } else {
                        ToastUtils.show(SearchActivity.this, R.string.search_city_failed);
                    }
                } else {
                    ToastUtils.show(SearchActivity.this, R.string.search_city_failed);
                }
                mAdapter.notifyDataSetChanged();
                hideLoading();
            }
        });
    }

    private void setFavoriteImg(List<Location> list){
        for(Location location : list){
            for(FavoriteEntity favoriteEntity : favoriteList){
                if(location.getName().equals(favoriteEntity.name)){
                    location.setFavorite(true);
                    break;
                }
            }
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        mAdapter = new SearchAdapter(this, mList);
        mAdapter.setSearchClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View v, final ImageView favo, int position) {
        final Location location = mList.get(position);
        if(location != null){
            final FavoriteEntity entity = new FavoriteEntity(location.getId(), location.getName(), location.getPath());
            viewModel.findFavoByName(location.getName()).observe(this, new Observer<FavoriteEntity>() {
                @Override
                public void onChanged(@Nullable FavoriteEntity favoriteEntity) {
                    if(favoriteEntity == null){
                        viewModel.insertFavorite(entity);
                        location.setFavorite(true);
                        favo.setSelected(true);
                        ToastUtils.show(SearchActivity.this, R.string.save_success);
                    } else {
                        viewModel.deleteFavorite(entity.id);
                        location.setFavorite(false);
                        favo.setSelected(false);
                        ToastUtils.show(SearchActivity.this, R.string.delete_success);
                    }
                }
            });
        }
    }

    private void showLoading() {
        mLoading.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        mLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
