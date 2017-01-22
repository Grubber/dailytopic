package com.github.xtorrent.dailytopic.bookshelf

import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfRepository
import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/18.
 */
class BookshelfPresenter @Inject constructor(private val repository: BookshelfRepository,
                                             private val view: BookshelfContract.View) : BookshelfContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    private var _pageNumber = 1
    private val _data by lazy {
        arrayListOf<Bookshelf>()
    }

    override fun setPageNumber(pageNumber: Int) {
        _pageNumber = pageNumber
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getBookshelfList(_pageNumber)
                .applySchedulers()
                .bind {
                    next {
                        if (it != null) {
                            _data.addAll(it.second)
                            view.setContentView(it, false)
                        }
                    }

                    error {
                        if (_data.isEmpty()) {
                            view.setErrorView()
                        } else {
                            view.setContentView(Pair(null, null), true)
                        }
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}