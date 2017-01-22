package com.github.xtorrent.dailytopic.bookshelf

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/18.
 */
@Module
class BookshelfPresenterModule(val view: BookshelfContract.View) {
    @Provides
    fun provideBookshelfContractView(): BookshelfContract.View {
        return view
    }
}