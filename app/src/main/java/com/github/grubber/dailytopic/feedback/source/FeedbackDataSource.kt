package com.github.grubber.dailytopic.feedback.source

import com.github.grubber.dailytopic.core.api.model.CommonResponse
import com.github.grubber.dailytopic.feedback.model.Feedback
import rx.Observable

/**
 * Created by grubber on 2017/2/3.
 */
interface FeedbackDataSource {
    fun feedback(feedback: Feedback): Observable<CommonResponse>
}