package com.github.grubber.dailytopic.voice.details

import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView

/**
 * Created by grubber on 2017/1/23.
 */
interface VoiceDetailsContract {
    interface View : BaseView<Presenter, String>

    interface Presenter : BasePresenter {
        fun setUrl(url: String)
    }
}