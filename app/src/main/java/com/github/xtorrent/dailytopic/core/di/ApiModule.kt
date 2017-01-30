package com.github.xtorrent.dailytopic.core.di

import com.github.xtorrent.dailytopic.core.BASE_ENDPOINT
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
    fun provideConverterFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}