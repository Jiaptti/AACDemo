package com.aacdemo.persistence.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aacdemo.AppContext;
import com.aacdemo.persistence.dao.CityDao;
import com.aacdemo.persistence.dao.FavoriteDao;
import com.aacdemo.persistence.entity.CityEntity;
import com.aacdemo.persistence.entity.FavoriteEntity;
import com.aacdemo.repository.SpRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/29.
 */
@Database(entities = {CityEntity.class, FavoriteEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    private static final String DATABASE_NAME = "weather_db";
    private volatile static AppDataBase mInstance;

    public abstract FavoriteDao favoriteDao();
    public abstract CityDao cityDao();

    public static AppDataBase getInstance(){
        if(mInstance == null){
            synchronized (AppDataBase.class){
                if(mInstance == null){
                    mInstance = Room.databaseBuilder(AppContext.getAppContext(), AppDataBase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
                }
                checkCopyDB();
            }
        }
        return mInstance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    public static void init() {
        Log.d("tests", "database init...");
        mInstance = Room.databaseBuilder(AppContext.getAppContext(), AppDataBase.class, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build();
        checkCopyDB();
    }

    private static void checkCopyDB(){
        if (SpRepository.getInstance().hasCopyDB(AppContext.getAppContext())) {
            Log.d("test", "database already copied !");
            return;
        }
        Log.d("test", "database copying...");
        File file = AppContext.getAppContext().getDatabasePath(DATABASE_NAME);
        try {
            AssetManager am = AppContext.getAppContext().getAssets();
            InputStream is = am.open("weather_db");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
            is.close();
            Log.d("test", "copy database success !");
            SpRepository.getInstance().setCopyDB(AppContext.getAppContext(), true);
        } catch (IOException e) {
            Log.d("test", "copy database failed !");
            e.printStackTrace();
        }
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
