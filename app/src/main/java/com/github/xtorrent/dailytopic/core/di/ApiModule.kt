package com.github.xtorrent.dailytopic.core.di

import com.github.xtorrent.dailytopic.core.BASE_ENDPOINT
import com.github.xtorrent.dailytopic.core.BMOB_BASE_ENDPOINT
import com.github.xtorrent.dailytopic.core.di.qualifier.AppRestfulClient
import com.github.xtorrent.dailytopic.core.di.qualifier.BmobRestfulClient
import com.github.xtorrent.dailytopic.core.di.qualifier.GsonConverter
import com.github.xtorrent.dailytopic.core.di.qualifier.ScalarsConverter
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by grubber on 2017/1/30.
 */
@Module
class ApiModule {
    @Provides
    @ApplicationScope
    fun provideGson() = GsonBuilder().create()

    @Provides
    @ApplicationScope
    @GsonConverter
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides
    @ApplicationScope
    @ScalarsConverter
    fun provideScalarsConverterFactory(): Converter.Factory = ScalarsConverterFactory.create()

    @Provides
    @ApplicationScope
    fun provideRxJavaCallAdapterFactory(): CallAdapter.Factory = RxJavaCallAdapterFactory.create()

    @Provides
    @ApplicationScope
    @AppRestfulClient
    fun provideRetrofitForApp(okHttpClient: OkHttpClient,
                              @ScalarsConverter scalarsConverterFactory: Converter.Factory,
                              rxJavaCallAdapterFactory: CallAdapter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(scalarsConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build()
    }

    @Provides
    @ApplicationScope
    @BmobRestfulClient
    fun provideRetrofitForBmob(okHttpClient: OkHttpClient,
                               @GsonConverter gsonConverterFactory: Converter.Factory,
                               rxJavaCallAdapterFactory: CallAdapter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BMOB_BASE_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build()
    }
}