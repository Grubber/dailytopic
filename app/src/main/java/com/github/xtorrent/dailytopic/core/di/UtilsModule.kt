package com.github.xtorrent.dailytopic.core.di

import android.content.Context
import com.github.xtorrent.dailytopic.core.di.qualifier.ForApplication
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import com.github.xtorrent.dailytopic.utils.ToastHelper
import dagger.Module
import dagger.Provides

/**
 * @author Grubber
 */
@Module
class UtilsModule {
    @Provides
    @ApplicationScope
    fun provideToastHelper(@ForApplication context: Context) = ToastHelper(context)
}