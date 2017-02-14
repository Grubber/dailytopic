package com.github.grubber.dailytopic.favourite

import com.github.grubber.dailytopic.article.model.Article
import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView

/**
 * Created by grubber on 2017/1/30.
 */
interface FavouriteContract {
    interface View : BaseView<Presenter, List<Article>>

    interface Presenter : BasePresenter
}