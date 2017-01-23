package com.github.xtorrent.dailytopic.bookshelf.chapter

import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfRepository
import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
class ChapterPresenter @Inject constructor(private val view: ChapterContract.View,
                                           private val repository: BookshelfRepository) : ChapterContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    private lateinit var _url: String

    override fun setUrl(url: String) {
        _url = url
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getChapter(_url)
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