package com.github.grubber.dailytopic.article

import dagger.Module
import dagger.Provides

/**
 * @author Grubber
 */
@Module
class ArticlePresenterModule(val view: ArticleContract.View) {
    @Provides
    fun provideArticleContractView(): ArticleContract.View {
        return view
    }
}