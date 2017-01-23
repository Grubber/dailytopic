package com.github.xtorrent.dailytopic.bookshelf.details

import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfRepository
import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/22.
 */
class BookshelfDetailsPresenter @Inject constructor(private val view: BookshelfDetailsContract.View,
                                                    private val repository: BookshelfRepository) : BookshelfDetailsContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    override fun setUrl(url: String) {
        _url = url
    }

    private lateinit var _url: String

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getBookshelfDetails(_url)
                .applySchedulers()
                .bind {
                    next {
                        if (it != null) {
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