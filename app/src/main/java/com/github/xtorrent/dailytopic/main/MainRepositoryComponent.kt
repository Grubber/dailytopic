package com.github.xtorrent.dailytopic.main

import com.github.xtorrent.dailytopic.article.ArticlePresenterComponent
import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.book.BookPresenterModule
import com.github.xtorrent.dailytopic.book.source.BookRepositoryModule
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/18.
 */
@MainRepositoryScope
@Subcomponent(modules = arrayOf(ArticleRepositoryModule::class, BookRepositoryModule::class))
interface MainRepositoryComponent {
    fun plus(articlePresenterModule: ArticlePresenterModule,
             bookPresenterModule: BookPresenterModule): MainPresenterComponent

    fun plus(articlePresenterModule: ArticlePresenterModule): ArticlePresenterComponent
}