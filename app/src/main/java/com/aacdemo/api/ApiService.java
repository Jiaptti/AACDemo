package com.aacdemo.api;

import android.util.Log;

import com.aacdemo.AppContext;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class ApiService {
    private static final String DATA_PATH = AppContext.getAppContext().getCacheDir().getAbsolutePath() + File.separator + "data";
    private static final String NET_DATA_PATH = DATA_PATH + File.separator + "net_cache";
    private static final String BASE_URL = "https://api.seniverse.com/v3/";
    private Retrofit mRetrofit;
    private OkHttpClient client;
    private Api mApi;

    private static class SingletonHolder{
        private static ApiService mApi = new ApiService();
    }
    public static ApiService getInstance(){
        return SingletonHolder.mApi;
    }

    private ApiService(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);//日志显示级别

        Cache cache = new Cache(new File(NET_DATA_PATH), 1024 * 1024 * 10);
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(new LoggingInterceptor())
                .cache(cache)
//                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
//                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public Api getApi() {
        if (mApi == null) {
            mApi = mRetrofit.create(Api.class);
        }
        return mApi;
    }

    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            //不能直接使用response.body().string()的方式输出日志，
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
//            Log.d(TAG, responseBody.string());
            return response;
        }
    }
}
