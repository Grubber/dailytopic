package com.github.xtorrent.dailytopic.favourite

import com.github.xtorrent.dailytopic.article.source.ArticleRepository
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/30.
 */
class FavouritePresenter @Inject constructor(private val view: FavouriteContract.View,
                                             private val repository: ArticleRepository) : FavouriteContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    override fun subscribe() {
        _binder.clear()


    }

    override fun unsubscribe() {
        _binder.clear()
    }
}