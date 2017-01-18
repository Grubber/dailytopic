package com.github.xtorrent.dailytopic.book

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.book.model.Book

/**
 * Created by grubber on 2017/1/18.
 */
interface BookContract {
    interface View : BaseView<Presenter, List<Book>>

    interface Presenter : BasePresenter
}