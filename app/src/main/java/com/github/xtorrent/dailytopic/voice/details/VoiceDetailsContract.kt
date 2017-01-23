package com.github.xtorrent.dailytopic.voice.details

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.voice.model.Voice

/**
 * Created by grubber on 2017/1/23.
 */
interface VoiceDetailsContract {
    interface View : BaseView<Presenter, Voice>

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}