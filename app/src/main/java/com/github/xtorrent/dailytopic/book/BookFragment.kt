package com.github.xtorrent.dailytopic.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.book.model.Book

/**
 * @author Grubber
 */
class BookFragment : ContentFragment(), BookContract.View {
    companion object {
        fun newInstance(): BookFragment {
            return BookFragment()
        }
    }

    private lateinit var _presenter: BookContract.Presenter

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_book, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.subscribe()
    }

    override fun setContentView(data: List<Book>) {
        // TODO
        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setLoadingView() {
        displayLoadingView()
    }

    override fun setPresenter(presenter: BookContract.Presenter) {
        _presenter = presenter
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }
}