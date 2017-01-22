package com.github.xtorrent.dailytopic.book

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.book.model.Bookshelf
import com.github.xtorrent.dailytopic.book.model.BookshelfHeaderImage

/**
 * Created by grubber on 2017/1/18.
 */
interface BookshelfContract {
    interface View : BaseView<Presenter, List<Bookshelf>> {
        fun setContentView(pair: Pair<List<BookshelfHeaderImage>?, List<Bookshelf>?>, loadedError: Boolean)
    }

    interface Presenter : BasePresenter {
        fun setPageNumber(pageNumber: Int)
    }
}