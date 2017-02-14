package com.github.grubber.dailytopic.bookshelf.chapter

import com.github.grubber.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/23.
 */
@ViewScope
@Subcomponent(modules = arrayOf(ChapterPresenterModule::class))
interface ChapterPresenterComponent {
    fun inject(chapterActivity: ChapterActivity)
}