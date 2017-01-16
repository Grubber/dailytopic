package com.github.xtorrent.dailytopic.article

import com.github.xtorrent.dailytopic.article.random.RandomArticleActivity
import com.github.xtorrent.dailytopic.core.di.scope.FragmentScope
import com.github.xtorrent.dailytopic.main.MainFragment
import dagger.Subcomponent

/**
 * @author Grubber
 */
@FragmentScope
@Subcomponent(modules = arrayOf(ArticlePresenterModule::class))
interface ArticlePresenterComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(randomArticleActivity: RandomArticleActivity)
}