package com.github.xtorrent.dailytopic.bookshelf.detail

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf

/**
 * Created by grubber on 2017/1/22.
 */
interface BookshelfDetailsContract {
    interface View : BaseView<Presenter, Pair<Bookshelf, List<Book>>>

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}