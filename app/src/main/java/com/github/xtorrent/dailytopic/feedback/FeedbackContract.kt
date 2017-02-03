package com.github.xtorrent.dailytopic.feedback

import com.github.xtorrent.dailytopic.base.BasePresenter
import com.github.xtorrent.dailytopic.base.BaseView
import com.github.xtorrent.dailytopic.core.api.model.CommonResponse
import com.github.xtorrent.dailytopic.feedback.model.Feedback

/**
 * Created by grubber on 2017/2/3.
 */
interface FeedbackContract {
    interface View : BaseView<Presenter, CommonResponse> {
        fun shouldShowLoadingDialog(isLoading: Boolean)
    }

    interface Presenter : BasePresenter {
        fun setFeedback(feedback: Feedback)
    }
}