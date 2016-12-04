package com.github.xtorrent.dailytopic.article

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView

/**
 * @author Grubber
 */
interface ArticleContract {
    interface View : BaseView<Presenter> {
        fun setLoadingView()
        fun setErrorView()
        fun setContentView(article: Article)
    }

    interface Presenter : BasePresenter
}