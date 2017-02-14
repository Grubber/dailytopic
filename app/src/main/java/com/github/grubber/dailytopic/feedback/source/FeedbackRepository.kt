package com.github.grubber.dailytopic.feedback.source

import com.github.grubber.dailytopic.core.api.model.CommonResponse
import com.github.grubber.dailytopic.feedback.model.Feedback
import com.github.grubber.dailytopic.feedback.service.FeedbackService
import rx.Observable
import javax.inject.Inject

/**
 * Created by grubber on 2017/2/3.
 */
@FeedbackScope
class FeedbackRepository @Inject constructor(private val feedbackService: FeedbackService) : FeedbackDataSource {
    override fun feedback(feedback: Feedback): Observable<CommonResponse> {
        return feedbackService.feedback(feedback)
    }
}