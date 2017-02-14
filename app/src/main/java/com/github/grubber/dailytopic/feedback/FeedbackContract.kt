package com.github.grubber.dailytopic.feedback

import com.github.grubber.dailytopic.base.BasePresenter
import com.github.grubber.dailytopic.base.BaseView
import com.github.grubber.dailytopic.core.api.model.CommonResponse
import com.github.grubber.dailytopic.feedback.model.Feedback

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