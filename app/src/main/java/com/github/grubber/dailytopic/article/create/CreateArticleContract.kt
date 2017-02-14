package com.github.grubber.dailytopic.article.create

import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView

/**
 * Created by grubber on 2017/1/30.
 */
interface CreateArticleContract {
    interface View : BaseView<Presenter, Any> {
        fun showLoadingDialog(loading: Boolean)
    }

    interface Presenter : BasePresenter {
        fun setData(title: String,
                    author: String,
                    content: String,
                    deliver: String,
                    source: String)
    }
}