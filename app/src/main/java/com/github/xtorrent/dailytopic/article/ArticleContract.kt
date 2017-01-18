package com.github.xtorrent.dailytopic.article

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView

/**
 * @author Grubber
 */
interface ArticleContract {
    interface View : BaseView<Presenter, Article>

    interface Presenter : BasePresenter {
        fun isRandom(isRandom: Boolean)
    }
}