package com.github.grubber.dailytopic.settings

import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.base.BaseFragment

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