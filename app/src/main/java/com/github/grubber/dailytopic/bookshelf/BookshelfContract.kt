package com.github.grubber.dailytopic.bookshelf

import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView
import com.github.grubber.dailytopic.bookshelf.model.Book
import com.github.grubber.dailytopic.bookshelf.model.BookshelfHeaderImage

/**
 * Created by grubber on 2017/1/18.
 */
interface BookshelfContract {
    interface View : BaseView<Presenter, List<Book>> {
        fun setContentView(pair: Pair<List<BookshelfHeaderImage>?, List<Book>?>, loadedError: Boolean)
    }

    interface Presenter : BasePresenter {
        fun setPageNumber(pageNumber: Int)
    }
}