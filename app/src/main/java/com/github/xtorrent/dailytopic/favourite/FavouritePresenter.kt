package com.github.xtorrent.dailytopic.favourite

import com.github.xtorrent.dailytopic.article.source.ArticleRepository
import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.utils.plusAssign
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

        _binder += repository.getFavouriteArticleList()
                .applySchedulers()
                .bind {
                    next {
                        it?.let {
                            view.setContentView(it)
                        }
                    }

                    error {
                        view.setErrorView()
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}