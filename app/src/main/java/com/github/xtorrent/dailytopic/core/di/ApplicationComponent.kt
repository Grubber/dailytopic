package com.github.xtorrent.dailytopic.core.di

import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryComponent
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.book.source.BookRepositoryComponent
import com.github.xtorrent.dailytopic.book.source.BookRepositoryModule
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import dagger.Component

/**
 * @author Grubber
 */
@ApplicationScope
@Component(modules = arrayOf(AndroidModule::class, DataModule::class, UtilsModule::class, NetworkModule::class))
interface ApplicationComponent {
    fun plus(articleRepositoryModule: ArticleRepositoryModule): ArticleRepositoryComponent
    fun plus(bookRepositoryModule: BookRepositoryModule): BookRepositoryComponent

    fun inject(application: DTApplication)
}