package com.github.xtorrent.dailytopic.bookshelf.chapter

import com.github.xtorrent.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/23.
 */
@ViewScope
@Subcomponent(modules = arrayOf(ChapterPresenterModule::class))
interface ChapterPresenterComponent {
    fun inject(chapterActivity: ChapterActivity)
}