package com.github.xtorrent.dailytopic.voice

import com.github.xtorrent.dailytopic.base.BaseFragment

/**
 * @author Grubber
 */
class VoiceFragment : BaseFragment() {
    companion object {
        fun newInstance(): VoiceFragment {
            return VoiceFragment()
        }
    }

    override fun getTitle(): String? {
        return null
    }
}