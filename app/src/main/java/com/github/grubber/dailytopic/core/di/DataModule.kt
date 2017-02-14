package com.github.grubber.dailytopic.core.di

import android.content.Context
import android.content.SharedPreferences
import com.github.grubber.dailytopic.core.EventBus
import com.github.grubber.dailytopic.core.di.qualifier.ForApplication
import com.github.grubber.dailytopic.core.di.scope.ApplicationScope
import com.squareup.otto.Bus
import dagger.Module
import dagger.Provides

/**
 * @author Grubber
 */
@Module
class DataModule {
    @Provides
    @ApplicationScope
    fun provideSharedPreferences(@ForApplication context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @ApplicationScope
    fun provideEventBus(): Bus {
        return EventBus
    }
}