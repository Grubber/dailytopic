package com.github.grubber.dailytopic.bookshelf.details

import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView
import com.github.grubber.dailytopic.bookshelf.model.Book
import com.github.grubber.dailytopic.bookshelf.model.Chapter

/**
 * Created by grubber on 2017/1/22.
 */
interface BookshelfDetailsContract {
    interface View : BaseView<Presenter, Pair<Book, List<Chapter>>>

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}