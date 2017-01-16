package com.github.xtorrent.dailytopic.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * @author Grubber
 */
class FavouriteFragment : BaseFragment() {
    companion object {
        fun newInstance(): FavouriteFragment {
            return FavouriteFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun getTitle(): String? {
        return null
    }
}