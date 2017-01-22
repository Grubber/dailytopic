package com.github.xtorrent.dailytopic.bookshelf.detail

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/22.
 */
@Module
class BookshelfDetailsPresenterModule(val view: BookshelfDetailsContract.View) {
    @Provides
    fun provideBookshelfDetailsContractView(): BookshelfDetailsContract.View {
        return view
    }
}