package com.github.xtorrent.dailytopic.core.di

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import com.github.xtorrent.dailytopic.core.di.qualifier.ClientVersionCode
import com.github.xtorrent.dailytopic.core.di.qualifier.ClientVersionName
import com.github.xtorrent.dailytopic.core.di.qualifier.ForApplication
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import com.github.xtorrent.dailytopic.utils.checkNotNull
import dagger.Module
import dagger.Provides

/**
 * @author Grubber
 */
@Module
class AndroidModule(context: Context) {
    private val _context: Context

    init {
        _context = checkNotNull(context, "Application context can't be null.")
    }

    @Provides
    @ApplicationScope
    @ForApplication
    fun provideApplicationContext(): Context {
        return _context
    }

    @ApplicationScope
    @Provides
    @ClientVersionCode
    fun provideVersionCode(@ForApplication context: Context): Int {
        return _getVersionCode(context)
    }

    @ApplicationScope
    @Provides
    @ClientVersionName
    fun provideVersionName(@ForApplication context: Context): String {
        return _getVersionName(context)
    }

    @ApplicationScope
    @Provides
    fun provideAssetManager(@ForApplication context: Context): AssetManager {
        return context.assets
    }

    private fun _getVersionCode(context: Context): Int {
        val pi = _getPackageInfo(context)
        if (pi != null) {
            return pi.versionCode
        }
        return 0
    }

    private fun _getVersionName(context: Context): String {
        val pi = _getPackageInfo(context)
        if (pi != null) {
            return pi.versionName
        } else {
            return ""
        }
    }

    private fun _getPackageInfo(context: Context): PackageInfo? {
        val pm = context.packageManager
        var pi: PackageInfo? = null
        try {
            pi = pm.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return pi
    }
}