package com.github.xtorrent.dailytopic.book

import com.github.xtorrent.dailytopic.book.model.Book
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

    private var _pageNumber = 1
    private val _data by lazy {
        arrayListOf<Book>()
    }

    override fun setPageNumber(pageNumber: Int) {
        _pageNumber = pageNumber
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getBookList(_pageNumber)
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