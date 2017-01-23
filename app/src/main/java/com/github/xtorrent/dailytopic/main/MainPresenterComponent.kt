package com.github.xtorrent.dailytopic.main

import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import com.github.xtorrent.dailytopic.bookshelf.BookshelfPresenterModule
import com.github.xtorrent.dailytopic.core.di.scope.ViewScope
import com.github.xtorrent.dailytopic.voice.VoicePresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/19.
 */
@ViewScope
@Subcomponent(modules = arrayOf(ArticlePresenterModule::class, BookshelfPresenterModule::class, VoicePresenterModule::class))
interface MainPresenterComponent {
    fun inject(mainFragment: MainFragment)
}