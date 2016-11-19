package com.github.xtorrent.dailytopic.settings

import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * @author Grubber
 */
class SettingsFragment : BaseFragment() {
    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_settings)
    }
}