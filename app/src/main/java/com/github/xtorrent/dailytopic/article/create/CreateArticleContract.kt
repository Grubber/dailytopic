package com.github.xtorrent.dailytopic.article.create

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView

/**
 * Created by grubber on 2017/1/30.
 */
interface CreateArticleContract {
    interface View : BaseView<Presenter, Any>

    interface Presenter : BasePresenter {
        fun setData(title: String,
                    author: String,
                    content: String,
                    deliver: String,
                    source: String)
    }
}