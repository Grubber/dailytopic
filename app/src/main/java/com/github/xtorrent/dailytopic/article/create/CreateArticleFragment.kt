package com.github.xtorrent.dailytopic.article.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment
import com.github.xtorrent.dailytopic.widget.dialog.LoadingDialog
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.textChanges
import rx.Observable

/**
 * Created by grubber on 2017/1/30.
 */
class CreateArticleFragment : BaseFragment(), CreateArticleContract.View {
    companion object {
        fun newInstance(): CreateArticleFragment {
            return CreateArticleFragment()
        }
    }

    private lateinit var _presenter: CreateArticleContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_create_article, container, false)
    }

    private val _titleView by bindView<EditText>(R.id.titleView)
    private val _authorView by bindView<EditText>(R.id.authorView)
    private val _contentView by bindView<EditText>(R.id.contentView)
    private val _deliverView by bindView<EditText>(R.id.deliverView)
    private val _sourceView by bindView<EditText>(R.id.sourceView)
    private val _submitButton by bindView<Button>(R.id.submitButton)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _contentView.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }

        bindSubscribe(Observable.combineLatest(_titleView.textChanges(), _authorView.textChanges(), _contentView.textChanges()) {
            title, author, content ->
            !title.isNullOrBlank() && !author.isNullOrBlank() && !content.isNullOrBlank()
        }) {
            _submitButton.isEnabled = it
        }
        bindSubscribe(_submitButton.clicks()) {
            _presenter.setData(_titleView.text.toString(),
                    _authorView.text.toString(),
                    _contentView.text.toString(),
                    _deliverView.text.toString(),
                    _sourceView.text.toString())
            _presenter.subscribe()
        }
    }

    override fun showLoadingDialog(loading: Boolean) {
        if (loading) _dialog.show() else _dialog.dismiss()
    }

    private val _dialog by lazy {
        LoadingDialog.create(context)
    }

    override fun setContentView(data: Any) {
        _titleView.text.clear()
        _authorView.text.clear()
        _contentView.text.clear()
        _deliverView.text.clear()
        _sourceView.text.clear()
        toastHelper().show(R.string.message_create_article_succeed)
    }

    override fun setErrorView() {
        toastHelper().show(R.string.message_create_article_failed)
    }

    override fun setPresenter(presenter: CreateArticleContract.Presenter) {
        _presenter = presenter
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_create)
    }
}