package com.github.xtorrent.dailytopic

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfRepositoryModule
import com.github.xtorrent.dailytopic.core.di.*
import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.main.MainRepositoryComponent
import com.github.xtorrent.dailytopic.utils.DeviceUtils
import com.github.xtorrent.dailytopic.utils.ToastHelper
import com.github.xtorrent.dailytopic.voice.source.VoiceRepositoryModule
import com.squareup.picasso.Picasso
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * @author Grubber
 */
class DTApplication : MultiDexApplication() {
    companion object {
        fun from(context: Context): DTApplication {
            return context.applicationContext as DTApplication
        }
    }

    var applicationComponent by Delegates.notNull<ApplicationComponent>()
    var mainRepositoryComponent by Delegates.notNull<MainRepositoryComponent>()

    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var toastHelper: ToastHelper
    @Inject
    lateinit var deviceUtils: DeviceUtils

    override fun onCreate() {
        super.onCreate()

        _setupObjectGraph()
        _setupAnalytics()

        databaseManager.open()
    }

    private fun _setupObjectGraph() {
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .dataModule(DataModule())
                .utilsModule(UtilsModule())
                .build()
        mainRepositoryComponent = applicationComponent.plus(
                ArticleRepositoryModule(),
                BookshelfRepositoryModule(),
                VoiceRepositoryModule()
        )
        applicationComponent.inject(this)
    }

    private fun _setupAnalytics() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}