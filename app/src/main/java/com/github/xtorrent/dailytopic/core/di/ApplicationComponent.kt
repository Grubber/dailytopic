package com.github.xtorrent.dailytopic.core.di

import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfRepositoryModule
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import com.github.xtorrent.dailytopic.main.MainRepositoryComponent
import dagger.Component

/**
 * @author Grubber
 */
@ApplicationScope
@Component(modules = arrayOf(AndroidModule::class, DataModule::class, UtilsModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun plus(articleRepositoryModule: ArticleRepositoryModule,
             bookRepositoryModule: BookshelfRepositoryModule): MainRepositoryComponent

    fun inject(application: DTApplication)
}