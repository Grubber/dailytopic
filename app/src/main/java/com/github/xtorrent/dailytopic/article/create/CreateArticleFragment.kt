package com.github.xtorrent.dailytopic.article.create

import android.os.Bundle
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * Created by grubber on 2017/1/30.
 */
class CreateArticleFragment : BaseFragment() {
    companion object {
        fun newInstance(): CreateArticleFragment {
            return CreateArticleFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_create)
    }
}