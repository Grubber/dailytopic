package com.github.xtorrent.dailytopic.feedback.source

import com.github.xtorrent.dailytopic.core.api.model.CommonResponse
import com.github.xtorrent.dailytopic.feedback.model.Feedback
import com.github.xtorrent.dailytopic.feedback.service.FeedbackService
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