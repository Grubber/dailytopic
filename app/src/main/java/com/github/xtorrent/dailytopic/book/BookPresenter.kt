package com.github.xtorrent.dailytopic.book

import com.github.xtorrent.dailytopic.book.source.BookRepository
import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/18.
 */
class BookPresenter @Inject constructor(private val repository: BookRepository,
                                        private val view: BookContract.View) : BookContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getBookList()
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