package com.github.xtorrent.dailytopic.article.create

import com.github.xtorrent.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/30.
 */
@ViewScope
@Subcomponent(modules = arrayOf(CreateArticlePresenterModule::class))
interface CreateArticlePresenterComponent {
    fun inject(createArticleActivity: CreateArticleActivity)
}