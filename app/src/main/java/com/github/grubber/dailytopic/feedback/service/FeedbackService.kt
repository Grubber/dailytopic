package com.github.grubber.dailytopic.feedback.service

import com.github.grubber.dailytopic.core.api.model.CommonResponse
import com.github.grubber.dailytopic.feedback.model.Feedback
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by grubber on 2017/2/3.
 */
interface FeedbackService {
    @POST("classes/feedback")
    fun feedback(@Body feedback: Feedback): Observable<CommonResponse>
}