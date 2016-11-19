package com.github.xtorrent.dailytopic.feedback

import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * @author Grubber
 */
class FeedbackFragment : BaseFragment() {
    companion object {
        fun newInstance(): FeedbackFragment {
            return FeedbackFragment()
        }
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_feedback)
    }
}