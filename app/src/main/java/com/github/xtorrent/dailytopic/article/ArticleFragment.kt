package com.github.xtorrent.dailytopic.article

import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.article.random.RandomArticleActivity
import com.github.xtorrent.dailytopic.base.BaseActivity
import com.github.xtorrent.dailytopic.base.ContentFragment

/**
 * @author Grubber
 */
class ArticleFragment : ContentFragment(), ArticleContract.View {
    companion object {
        private const val EXTRA_IS_RANDOM = "isRandom"

        fun newInstance(isRandom: Boolean): ArticleFragment {
            val fragment = ArticleFragment()
            val args = Bundle()
            args.putBoolean(EXTRA_IS_RANDOM, isRandom)
            fragment.arguments = args
            return fragment
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

    private val _isRandom by lazy {
        arguments.getBoolean(EXTRA_IS_RANDOM)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        _presenter.subscribe()
        _presenter.isRandom(_isRandom)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_article, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        if (_isRandom) {
            menu?.findItem(R.id.random)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.random -> {
                RandomArticleActivity.start(context)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return if (_isRandom) getString(R.string.title_random_article) else (activity as BaseActivity).supportActionBar?.title as String?
    }
}