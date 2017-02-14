package com.github.grubber.dailytopic.core.di

import android.content.Context
import com.github.grubber.dailytopic.core.di.qualifier.ForApplication
import com.github.grubber.dailytopic.core.di.scope.ApplicationScope
import com.github.grubber.dailytopic.utils.DeviceUtils
import com.github.grubber.dailytopic.utils.ToastHelper
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

    @Provides
    @ApplicationScope
    fun provideDeviceUtils(@ForApplication context: Context) = DeviceUtils(context)
}