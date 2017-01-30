package com.github.xtorrent.dailytopic.article.create

import com.github.xtorrent.dailytopic.article.source.ArticleRepository
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/30.
 */
class CreateArticlePresenter @Inject constructor(private val view: CreateArticleContract.View,
                                                 private val repository: ArticleRepository) : CreateArticleContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    override fun subscribe() {
        _binder.clear()


    }

    override fun unsubscribe() {
        _binder.clear()
    }
}