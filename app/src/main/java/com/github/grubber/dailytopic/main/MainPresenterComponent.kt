package com.github.grubber.dailytopic.main

import com.github.grubber.dailytopic.article.ArticlePresenterModule
import com.github.grubber.dailytopic.bookshelf.BookshelfPresenterModule
import com.github.grubber.dailytopic.core.di.scope.ViewScope
import com.github.grubber.dailytopic.favourite.FavouritePresenterModule
import com.github.grubber.dailytopic.voice.VoicePresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/19.
 */
@ViewScope
@Subcomponent(modules = arrayOf(ArticlePresenterModule::class, BookshelfPresenterModule::class,
        VoicePresenterModule::class, FavouritePresenterModule::class))
interface MainPresenterComponent {
    fun inject(mainFragment: MainFragment)
}