package com.github.grubber.dailytopic.core.di

import android.content.Context
import android.content.res.AssetManager
import com.github.grubber.dailytopic.core.di.qualifier.ForApplication
import com.github.grubber.dailytopic.core.di.scope.ApplicationScope
import com.github.grubber.dailytopic.utils.checkNotNull
import dagger.Module
import dagger.Provides

/**
 * @author Grubber
 */
@Module
class AndroidModule(context: Context) {
    private val _applicationContext: Context = checkNotNull(context, "Application context can't be null.")

    @Provides
    @ApplicationScope
    @ForApplication
    fun provideApplicationContext(): Context {
        return _applicationContext
    }

    @ApplicationScope
    @Provides
    fun provideAssetManager(@ForApplication context: Context): AssetManager {
        return context.assets
    }
}