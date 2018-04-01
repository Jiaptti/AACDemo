package com.aacdemo.repository;

import android.arch.lifecycle.LiveData;

import com.aacdemo.persistence.database.AppDataBase;
import com.aacdemo.persistence.entity.FavoriteEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class FavoriteRepository {

    public static class SingletonHolder{
        private static FavoriteRepository INSTANCE = new FavoriteRepository();
    }

    public static FavoriteRepository getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public LiveData<FavoriteEntity> getFavorite(String name){
        return AppDataBase.getInstance().favoriteDao().getFavorite(name);
    }

    public LiveData<List<FavoriteEntity>> loadFavorites(){
        return AppDataBase.getInstance().favoriteDao().loadFavorites();
    }

    public void insertFavorite(FavoriteEntity favoriteEntity){
        AppDataBase.getInstance().favoriteDao().insertFavorite(favoriteEntity);
    }

    public void deleteFavorite(String id){
        AppDataBase.getInstance().favoriteDao().deleteFavorite(id);
    }
}
