package com.github.grubber.dailytopic.bookshelf.chapter

import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView
import com.github.grubber.dailytopic.bookshelf.model.Chapter

/**
 * Created by grubber on 2017/1/23.
 */
interface ChapterContract {
    interface View : BaseView<Presenter, Chapter>

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}