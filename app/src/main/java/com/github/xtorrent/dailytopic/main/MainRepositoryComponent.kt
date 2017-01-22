package com.github.xtorrent.dailytopic.main

import com.github.xtorrent.dailytopic.article.ArticlePresenterComponent
import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.book.BookshelfPresenterModule
import com.github.xtorrent.dailytopic.book.source.BookshelfRepositoryModule
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/18.
 */
@MainRepositoryScope
@Subcomponent(modules = arrayOf(ArticleRepositoryModule::class, BookshelfRepositoryModule::class))
interface MainRepositoryComponent {
    fun plus(articlePresenterModule: ArticlePresenterModule,
             bookPresenterModule: BookshelfPresenterModule): MainPresenterComponent

    fun plus(articlePresenterModule: ArticlePresenterModule): ArticlePresenterComponent
}