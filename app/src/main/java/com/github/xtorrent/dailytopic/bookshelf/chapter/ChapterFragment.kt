package com.github.xtorrent.dailytopic.bookshelf.chapter

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter

/**
 * Created by grubber on 2017/1/23.
 */
class ChapterFragment : ContentFragment(), ChapterContract.View {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_URL = "url"

        fun newInstance(title: String, url: String): ChapterFragment {
            val fragment = ChapterFragment()
            val args = Bundle()
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    private val _title by lazy {
        arguments.getString(EXTRA_TITLE)
    }
    private val _url by lazy {
        arguments.getString(EXTRA_URL)
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_chapter, container, false)
    }

    private val _contentView by bindView<TextView>(R.id.contentView)

    private lateinit var _presenter: ChapterContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setUrl(_url)
        _presenter.subscribe()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setContentView(data: Chapter) {
        _contentView.text = Html.fromHtml(data.content())
        displayContentView()
    }

    override fun setPresenter(presenter: ChapterContract.Presenter) {
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
        return _title
    }
}