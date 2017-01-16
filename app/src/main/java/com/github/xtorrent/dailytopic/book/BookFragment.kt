package com.github.xtorrent.dailytopic.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_book, container, false)
    }

    override fun getTitle(): String? {
        return null
    }
}