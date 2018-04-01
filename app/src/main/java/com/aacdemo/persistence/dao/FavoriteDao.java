package com.aacdemo.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aacdemo.persistence.entity.FavoriteEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */
@Dao
public interface FavoriteDao {

    @Query("select * from favorite")
    public LiveData<List<FavoriteEntity>> loadFavorites();

    @Query("select * from favorite where name = :name")
    public LiveData<FavoriteEntity> getFavorite(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFavorite(FavoriteEntity favoriteEntity);

    @Query("delete from favorite where id=:id")
    public void deleteFavorite(String id);
}
