package com.github.xtorrent.dailytopic.favourite

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.base.ContentFragment

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

    private lateinit var _presenter: FavouriteContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _recyclerView.layoutManager = LinearLayoutManager(context)

        _presenter.subscribe()
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: FavouriteContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(data: List<Article>) {
        // TODO
        displayContentView()
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