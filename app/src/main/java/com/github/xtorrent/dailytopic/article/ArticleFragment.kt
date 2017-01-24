package com.github.xtorrent.dailytopic.article

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.base.BaseActivity
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.jakewharton.rxbinding.view.clicks
import com.squareup.picasso.MemoryPolicy

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
        _rootView = LayoutInflater.from(context).inflate(R.layout.fragment_article, container, false)
        return _rootView
    }

    private lateinit var _presenter: ArticleContract.Presenter

    override fun setPresenter(presenter: ArticleContract.Presenter) {
        _presenter = presenter
    }

    private lateinit var _rootView: View
    private lateinit var _backgroundView: ImageView
    private lateinit var _titleView: TextView
    private lateinit var _authorView: TextView
    private lateinit var _contentView: TextView
    private lateinit var _randomButton: TextView

    private val _isRandom by lazy {
        arguments.getBoolean(EXTRA_IS_RANDOM)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        _initView()
        _randomButton.visibility = if (_isRandom) View.VISIBLE else View.GONE

        _presenter.isRandom(_isRandom)
        _presenter.subscribe()
    }

    private fun _initView() {
        _backgroundView = _rootView.findViewById(R.id.backgroundView) as ImageView
        _titleView = _rootView.findViewById(R.id.titleView) as TextView
        _authorView = _rootView.findViewById(R.id.authorView) as TextView
        _contentView = _rootView.findViewById(R.id.contentView) as TextView
        _randomButton = _rootView.findViewById(R.id.randomButton) as TextView

        bindSubscribe(_randomButton.clicks()) {
            _presenter.subscribe()
            displayLoadingView()
            _recycleBitmap()
            invalidateContentView()
            _initView()
        }
    }

    private fun _recycleBitmap() {
        val drawable = _backgroundView.drawable
        _backgroundView.setImageDrawable(null)
        val bitmap = (drawable as? BitmapDrawable)?.bitmap
        bitmap?.recycle()
    }

    override fun setContentView(data: Article) {
        picasso().load(data.backgroundImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .config(Bitmap.Config.RGB_565)
                .resize(deviceUtils().getScreenSize().x, deviceUtils().getScreenSize().y)
                .centerCrop()
                .into(_backgroundView)
        _titleView.text = data.title()
        _authorView.text = data.author()
        _contentView.text = Html.fromHtml(data.content())
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
                ArticleActivity.start(context)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        picasso().cancelRequest(_backgroundView)
        _recycleBitmap()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return if (_isRandom) getString(R.string.title_random_article) else (activity as BaseActivity).supportActionBar?.title as String?
    }
}