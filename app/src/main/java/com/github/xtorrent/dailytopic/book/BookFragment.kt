package com.github.xtorrent.dailytopic.book

import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * @author Grubber
 */
class BookFragment : BaseFragment() {
    companion object {
        fun newInstance(): BookFragment {
            return BookFragment()
        }
    }

    override fun getTitle(): String? {
        return null
    }
}