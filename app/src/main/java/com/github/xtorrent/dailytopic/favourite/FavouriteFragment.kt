package com.github.xtorrent.dailytopic.favourite

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

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_favourite)
    }
}