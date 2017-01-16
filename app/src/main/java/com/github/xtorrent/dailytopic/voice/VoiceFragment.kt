package com.github.xtorrent.dailytopic.voice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_voice, container, false)
    }

    override fun getTitle(): String? {
        return null
    }
}