package com.github.xtorrent.dailytopic.article

import com.github.xtorrent.dailytopic.article.source.ArticleRepository
import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * @author Grubber
 */
class ArticlePresenter @Inject constructor(private val repository: ArticleRepository,
                                           private val view: ArticleContract.View) : ArticleContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private var _isRandom: Boolean = false

    override fun isRandom(isRandom: Boolean) {
        _isRandom = isRandom
    }

    override fun subscribe() {
        _binder.clear()

        repository.getArticle(_isRandom)
                .applySchedulers()
                .bind {
                    next {
                        if (it != null) {
                            view.setContentView(it)
                        } else {
                            view.setErrorView()
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