package com.github.grubber.dailytopic.article.create

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/30.
 */
@Module
class CreateArticlePresenterModule(val view: CreateArticleContract.View) {
    @Provides
    fun provideCreateArticleContractView(): CreateArticleContract.View {
        return view
    }
}