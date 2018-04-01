package com.aacdemo.model.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aacdemo.api.ApiResponse;
import com.aacdemo.base.BaseResult;
import com.aacdemo.entity.Location;
import com.aacdemo.persistence.entity.FavoriteEntity;
import com.aacdemo.repository.CityRepository;
import com.aacdemo.repository.FavoriteRepository;
import com.aacdemo.utils.Config;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class SearchViewModel extends ViewModel{

    public LiveData<ApiResponse<BaseResult<Location>>> searchCity(String q){
        return CityRepository.getInstance().searchCity(Config.KEY, q, Config.LANG, Config.LIMIT, Config.OFFSET);
    }

    public LiveData<FavoriteEntity> findFavoByName(String name){
        return FavoriteRepository.getInstance().getFavorite(name);
    }

    public LiveData<List<FavoriteEntity>> loadFavorites(){
        return FavoriteRepository.getInstance().loadFavorites();
    }


    public void insertFavorite(final FavoriteEntity favoriteEntity){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FavoriteRepository.getInstance().insertFavorite(favoriteEntity);
            }
        }).start();
    }

    public void deleteFavorite(final String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FavoriteRepository.getInstance().deleteFavorite(id);
            }
        }).start();
    }
}
