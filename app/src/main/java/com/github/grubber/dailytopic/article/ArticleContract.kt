package com.github.grubber.dailytopic.article

import com.github.grubber.dailytopic.article.model.Article
import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView

/**
 * @author Grubber
 */
interface ArticleContract {
    interface View : BaseView<Presenter, Article> {
        fun setIsFavourite(isFavourite: Boolean)
    }

    interface Presenter : BasePresenter {
        fun isRandom(isRandom: Boolean)
        fun isFavourite()
        fun toggleFavourite(isFavourite: Boolean)
        fun setId(id: Long)
    }
}