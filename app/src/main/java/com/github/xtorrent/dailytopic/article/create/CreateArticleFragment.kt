package com.github.xtorrent.dailytopic.article.create

import android.os.Bundle
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.subscribe()
    }

    override fun setContentView(data: Any) {
        // Ignored.
    }

    override fun setErrorView() {
        // Ignored.
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