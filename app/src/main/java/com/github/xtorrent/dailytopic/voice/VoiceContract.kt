package com.github.xtorrent.dailytopic.voice

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.voice.model.Voice

/**
 * Created by grubber on 2017/1/23.
 */
interface VoiceContract {
    interface View : BaseView<Presenter, List<Voice>>

    interface Presenter : BasePresenter {
        fun setPageNumber(pageNumber: Int)
    }
}