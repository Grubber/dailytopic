package com.github.xtorrent.dailytopic.book

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.book.model.Book
import com.github.xtorrent.dailytopic.book.model.BookHeaderImage

/**
 * Created by grubber on 2017/1/18.
 */
interface BookContract {
    interface View : BaseView<Presenter, List<Book>> {
        fun setContentView(pair: Pair<List<BookHeaderImage>?, List<Book>?>, loadedError: Boolean)
    }

    interface Presenter : BasePresenter {
        fun setPageNumber(pageNumber: Int)
    }
}