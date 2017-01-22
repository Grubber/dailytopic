package com.github.xtorrent.dailytopic.bookshelf.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.base.PagingRecyclerViewAdapter
import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf
import com.squareup.picasso.Picasso

/**
 * Created by grubber on 2017/1/22.
 */
class BookshelfDetailsFragment : ContentFragment(), BookshelfDetailsContract.View {
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

    private lateinit var _presenter: BookshelfDetailsContract.Presenter

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)
    private val _adapter by lazy {
        BookshelfDetailsItemAdapter(picasso())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)
        _recyclerView.adapter = _adapter

        if (_url != null) {
            _presenter.setUrl(_url)
        } else {
            _adapter.headerItem = _bookshelf
            _adapter.notifyDataSetChanged()
            _presenter.setUrl(_bookshelf.url())
        }
        _presenter.subscribe()
    }

    override fun setContentView(data: Pair<Bookshelf, List<Book>>) {
        setTitle(data.first.title())
        if (_bookshelf == null) {
            _adapter.headerItem = data.first
        }
        _adapter.addItems(data.second, PagingRecyclerViewAdapter.STATE_LOADING_COMPLETED)
        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setLoadingView() {
        displayLoadingView()
    }

    override fun setPresenter(presenter: BookshelfDetailsContract.Presenter) {
        _presenter = presenter
    }

    class BookshelfDetailsItemAdapter(private val picasso: Picasso) : PagingRecyclerViewAdapter<Book, Bookshelf>() {
        override fun getLoadCount(): Int {
            // Ignored.
            return 0
        }

        override fun onLoadMore(pageNumber: Int) {
            // Ignored.
        }

        override fun shouldShowLoading(): Boolean {
            return false
        }

        override fun hasFooter(): Boolean {
            return false
        }

        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return BookshelfDetailsItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bookshelf_details, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as BookshelfDetailsItemViewHolder
            val item = getItem(position)
            holder.titleView.text = item.title()
            holder.itemView.setOnClickListener {
                // TODO
            }
        }

        override fun hasHeader(): Boolean {
            return true
        }

        override fun onBindHeaderView(holder: RecyclerView.ViewHolder, position: Int) {
            super.onBindHeaderView(holder, position)
            holder as BookshelfDetailsHeaderItemViewHolder
            picasso.load(headerItem!!.image())
                    .fit()
                    .into(holder.coverView)
            holder.titleView.text = headerItem!!.title()
            holder.authorView.text = headerItem!!.author()
        }

        override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
            return BookshelfDetailsHeaderItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_bookshelf_details_header, parent, false))
        }

        override fun onRetry(pageNumber: Int) {
            // Ignored.
        }

        class BookshelfDetailsHeaderItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            val coverView by bindView<ImageView>(R.id.coverView)
            val titleView by bindView<TextView>(R.id.titleView)
            val authorView by bindView<TextView>(R.id.authorView)
        }

        class BookshelfDetailsItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            val titleView by bindView<TextView>(R.id.titleView)
        }
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return _bookshelf?.title() ?: ""
    }
}