package com.github.xtorrent.dailytopic.article

import com.github.xtorrent.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * @author Grubber
 */
@ViewScope
@Subcomponent(modules = arrayOf(ArticlePresenterModule::class))
interface ArticlePresenterComponent {
    fun inject(articleActivity: ArticleActivity)
}