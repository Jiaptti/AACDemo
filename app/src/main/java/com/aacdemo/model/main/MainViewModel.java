package com.aacdemo.model.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aacdemo.api.ApiResponse;
import com.aacdemo.base.BaseResult;
import com.aacdemo.entity.WeatherEntity;
import com.aacdemo.persistence.entity.FavoriteEntity;
import com.aacdemo.repository.FavoriteRepository;
import com.aacdemo.repository.WeatherRepository;
import com.aacdemo.utils.Config;

import java.util.List;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class MainViewModel extends ViewModel{

    public LiveData<ApiResponse<BaseResult<WeatherEntity>>> getNowWeather(String location){
        return WeatherRepository.getInstance().getWeatherNow(Config.KEY, location, Config.LANG, Config.UNIT);
    }

    public LiveData<List<FavoriteEntity>> loadFavorite(){
        return FavoriteRepository.getInstance().loadFavorites();
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
