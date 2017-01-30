package com.github.xtorrent.dailytopic.favourite

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView

/**
 * Created by grubber on 2017/1/30.
 */
interface FavouriteContract {
    interface View : BaseView<Presenter, List<Article>>

    interface Presenter : BasePresenter
}