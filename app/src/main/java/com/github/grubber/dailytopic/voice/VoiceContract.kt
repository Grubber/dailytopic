package com.github.grubber.dailytopic.voice

import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView
import com.github.grubber.dailytopic.voice.model.Voice

/**
 * Created by grubber on 2017/1/23.
 */
interface VoiceContract {
    interface View : BaseView<Presenter, List<Voice>> {
        fun setContentView(data: List<Voice>?, loadedError: Boolean)
    }

    interface Presenter : BasePresenter {
        fun setPageNumber(pageNumber: Int)
    }
}