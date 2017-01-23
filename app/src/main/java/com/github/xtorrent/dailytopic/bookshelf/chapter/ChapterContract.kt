package com.github.xtorrent.dailytopic.bookshelf.chapter

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter

/**
 * Created by grubber on 2017/1/23.
 */
interface ChapterContract {
    interface View : BaseView<Presenter, Chapter>

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}