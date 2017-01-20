package com.github.xtorrent.dailytopic.book

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
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
import com.github.xtorrent.dailytopic.book.model.Book
import com.squareup.picasso.Picasso

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

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)

    private val _adapter by lazy {
        BookItemAdapter(context, _presenter, picasso())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = GridLayoutManager(context, 2)
        _recyclerView.adapter = _adapter

        _presenter.subscribe()
    }

    override fun setContentView(data: List<Book>) {
    }

    override fun setContentView(data: List<Book>?, loadedError: Boolean) {
        val loadingState = if (data == null && loadedError) {
            PagingRecyclerViewAdapter.STATE_LOADING_FAILED
        } else {
            PagingRecyclerViewAdapter.STATE_LOADING_SUCCEED
        }
        _adapter.addItems(data, loadingState)
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

    class BookItemAdapter(private val context: Context,
                          private val presenter: BookContract.Presenter,
                          private val picasso: Picasso) : PagingRecyclerViewAdapter<Book>() {
        override fun getLoadCount(): Int {
            return 12
        }

        override fun onLoadMore(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return BookItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as BookItemViewHolder
            val item = getItem(position)
            picasso.load(item.image())
                    .placeholder(ColorDrawable(R.color.colorAccent))
                    .error(ColorDrawable(R.color.colorAccent))
                    .fit()
                    .into(holder.coverView)
            holder.titleView.text = item.title()
            holder.authorView.text = item.author()
            holder.itemView.setOnClickListener {
                // TODO
            }
        }

        override fun onRetry(pageNumber: Int) {
            presenter.setPageNumber(pageNumber)
            presenter.subscribe()
        }

//        override fun hasHeader(): Boolean {
//            return true
//        }
//
//        override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
//            return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_header, parent, false)) {}
//        }
    }

    class BookItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val coverView by bindView<ImageView>(R.id.coverView)
        val titleView by bindView<TextView>(R.id.titleView)
        val authorView by bindView<TextView>(R.id.authorView)
    }
}