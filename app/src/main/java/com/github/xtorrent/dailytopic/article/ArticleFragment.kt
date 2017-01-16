package com.github.xtorrent.dailytopic.article

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.base.ContentFragment

/**
 * @author Grubber
 */
class ArticleFragment : ContentFragment(), ArticleContract.View {
    companion object {
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_article, container, false)
    }

    private lateinit var _presenter: ArticleContract.Presenter

    override fun setPresenter(presenter: ArticleContract.Presenter) {
        _presenter = presenter
    }

    private val _backgroundView by bindView<ImageView>(R.id.backgroundView)
    private val _titleView by bindView<TextView>(R.id.titleView)
    private val _authorView by bindView<TextView>(R.id.authorView)
    private val _contentView by bindView<TextView>(R.id.contentView)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.subscribe()
    }

    override fun setLoadingView() {
        displayLoadingView()
    }

    override fun setContentView(article: Article) {
        picasso().load(article.backgroundImage())
                .fit()
                .into(_backgroundView)
        _titleView.text = article.title()
        _authorView.text = article.author()
        _contentView.text = Html.fromHtml(article.content())
        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
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