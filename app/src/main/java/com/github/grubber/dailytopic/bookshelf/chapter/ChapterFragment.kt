package com.github.grubber.dailytopic.bookshelf.chapter

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.base.ContentFragment
import com.github.grubber.dailytopic.bookshelf.model.Chapter
import com.jakewharton.rxbinding.view.clicks
import java.util.*

/**
 * Created by grubber on 2017/1/23.
 */
class ChapterFragment : ContentFragment(), ChapterContract.View {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_DATA = "data"
        private const val EXTRA_INDEX = "index"

        fun newInstance(title: String, data: ArrayList<Chapter>, index: Int): ChapterFragment {
            val fragment = ChapterFragment()
            val args = Bundle()
            args.putString(EXTRA_TITLE, title)
            args.putParcelableArrayList(EXTRA_DATA, data)
            args.putInt(EXTRA_INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }

    private val _title by lazy {
        arguments.getString(EXTRA_TITLE)
    }
    private val _data by lazy {
        arguments.getParcelableArrayList<Chapter>(EXTRA_DATA)
    }
    private var _index = 0
    private lateinit var _chapter: Chapter

    override fun createContentView(container: ViewGroup): View {
        _rootView = LayoutInflater.from(context).inflate(R.layout.fragment_chapter, container, false)
        return _rootView
    }

    private lateinit var _rootView: View
    private lateinit var _contentView: TextView
    private lateinit var _nextView: TextView
    private lateinit var _previousView: TextView

    private lateinit var _presenter: ChapterContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _index = arguments.getInt(EXTRA_INDEX)
        _initView()
        _requestData()
    }

    private fun _initView() {
        _contentView = _rootView.findViewById<TextView>(R.id.contentView) as TextView
        _nextView = _rootView.findViewById<TextView>(R.id.nextView) as TextView
        _previousView = _rootView.findViewById<TextView>(R.id.previousView) as TextView

        bindSubscribe(_nextView.clicks()) {
            displayLoadingView()
            invalidateContentView()
            _initView()
            _index++
            _requestData()
        }
        bindSubscribe(_previousView.clicks()) {
            displayLoadingView()
            invalidateContentView()
            _initView()
            _index--
            _requestData()
        }
    }

    private fun _requestData() {
        _chapter = _data[_index]
        setTitle("(${_index + 1}/${_data.size}) $_title/${_chapter.title()}")

        _previousView.visibility = if (_index == 0) View.GONE else View.VISIBLE
        _nextView.visibility = if (_index == _data.size - 1) View.GONE else View.VISIBLE

        _presenter.setUrl(_chapter.url())
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
        return ""
    }
}