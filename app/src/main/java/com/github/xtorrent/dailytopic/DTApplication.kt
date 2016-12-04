package com.github.xtorrent.dailytopic

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryComponent
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.core.di.*
import com.github.xtorrent.dailytopic.db.DatabaseManager
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
    var articleRepositoryComponent by Delegates.notNull<ArticleRepositoryComponent>()

    @Inject
    lateinit var databaseManager: DatabaseManager

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

        articleRepositoryComponent = applicationComponent.plus(ArticleRepositoryModule())

        applicationComponent.inject(this)
    }

    private fun _setupAnalytics() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}