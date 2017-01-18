package com.github.xtorrent.dailytopic.main

import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import com.github.xtorrent.dailytopic.book.BookPresenterModule
import com.github.xtorrent.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/19.
 */
@ViewScope
@Subcomponent(modules = arrayOf(ArticlePresenterModule::class, BookPresenterModule::class))
interface MainPresenterComponent {
    fun inject(mainFragment: MainFragment)
}