package com.github.xtorrent.dailytopic.article.source

import com.github.xtorrent.dailytopic.article.ArticlePresenterComponent
import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import dagger.Subcomponent

/**
 * @author Grubber
 */
@ArticleScope
@Subcomponent(modules = arrayOf(ArticleRepositoryModule::class))
interface ArticleRepositoryComponent {
    fun plus(articlePresenterModule: ArticlePresenterModule): ArticlePresenterComponent
}