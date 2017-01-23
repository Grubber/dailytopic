package com.github.xtorrent.dailytopic.bookshelf.chapter

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/23.
 */
@Module
class ChapterPresenterModule(val view: ChapterContract.View) {
    @Provides
    fun provideChapterContractView(): ChapterContract.View {
        return view
    }
}