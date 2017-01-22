package com.github.xtorrent.dailytopic.bookshelf.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf

/**
 * Created by grubber on 2017/1/22.
 */
class BookshelfDetailsFragment : ContentFragment() {
    companion object {
        private const val EXTRA_BOOKSHELF = "bookshelf"
        private const val EXTRA_URL = "url"

        fun newInstance(bookshelf: Bookshelf?, url: String?): BookshelfDetailsFragment {
            val fragment = BookshelfDetailsFragment()
            val args = Bundle()
            bookshelf?.let {
                args.putParcelable(EXTRA_BOOKSHELF, it)
            }
            url?.let {
                args.putString(EXTRA_URL, it)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_bookshelf_details, container, false)
    }

    private val _bookshelf by lazy {
        arguments.getParcelable<Bookshelf>(EXTRA_BOOKSHELF)
    }
    private val _url by lazy {
        arguments.getString(EXTRA_URL)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onRetry() {
        // TODO
    }

    override fun getTitle(): String? {
        return _bookshelf?.title() ?: ""
    }
}