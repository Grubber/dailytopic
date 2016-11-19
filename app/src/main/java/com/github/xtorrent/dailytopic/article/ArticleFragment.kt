package com.github.xtorrent.dailytopic.article

import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * @author Grubber
 */
class ArticleFragment : BaseFragment() {
    companion object {
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

    override fun getTitle(): String? {
        return null
    }
}