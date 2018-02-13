package com.dreamers.explorer.dagger;

import android.app.Application;
import android.content.Context;

import com.dreamers.explorer.RetrofitService;
import com.dreamers.explorer.common.Constants;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by c029312 on 2/7/18.
 */
@Module
public class NetworkModule {

    @Provides
    public Context getContext(){
        return getContext();
    }

    @Provides
    public Application getApplication(Context context){
        return getApplication(context);
    }

    @Provides
    public Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .client(new OkHttpClient().newBuilder().build())
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public RetrofitService provideRetrofitService(Retrofit retrofit){
        return retrofit.create(RetrofitService.class);
    }
}
