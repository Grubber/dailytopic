package com.github.grubber.dailytopic.favourite

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.article.ArticleActivity
import com.github.grubber.dailytopic.article.model.Article
import com.github.grubber.dailytopic.base.ContentFragment
import com.github.grubber.dailytopic.base.PagingRecyclerViewAdapter

/**
 * @author Grubber
 */
class FavouriteFragment : ContentFragment(), FavouriteContract.View {
    companion object {
        fun newInstance(): FavouriteFragment {
            return FavouriteFragment()
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_favourite, container, false)
    }

    private val _recyclerView by bindView<RecyclerView>(R.id.recyclerView)
    private val _adapter by lazy {
        FavouriteItemAdapter(context)
    }

    private lateinit var _presenter: FavouriteContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)
        _recyclerView.adapter = _adapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            _adapter.getItems().clear()
            _adapter.notifyDataSetChanged()
            _presenter.subscribe()
        }
    }

    override fun onResume() {
        super.onResume()

        _adapter.getItems().clear()
        _adapter.notifyDataSetChanged()
        _presenter.subscribe()
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: FavouriteContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(data: List<Article>) {
        _adapter.addItems(data, PagingRecyclerViewAdapter.STATE_LOADING_COMPLETED)
        displayContentView()
    }

    class FavouriteItemAdapter(val context: Context) : PagingRecyclerViewAdapter<Article, Any>() {
        override fun getLoadCount(): Int {
            return 20
        }

        override fun onLoadMore(pageNumber: Int) {
            // Ignored.
        }

        override fun hasFooter(): Boolean {
            return false
        }

        override fun onCreateBasicItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return FavouriteItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false))
        }

        override fun onBindBasicItemView(holder: RecyclerView.ViewHolder, position: Int) {
            holder as FavouriteItemViewHolder
            val item = getItem(position)
            holder.titleView.text = item.title()
            holder.itemView.setOnClickListener {
                ArticleActivity.start(context, item._id())
            }
        }

        override fun onCreateEmptyViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_favourite_empty, parent, false)) {}
        }

        override fun onRetry(pageNumber: Int) {
            // Ignored.
        }
    }

    class FavouriteItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleView by bindView<TextView>(R.id.titleView)
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }
}