package com.github.xtorrent.dailytopic.feedback.source

import com.github.xtorrent.dailytopic.core.api.model.CommonResponse
import com.github.xtorrent.dailytopic.feedback.model.Feedback
import rx.Observable

/**
 * Created by grubber on 2017/2/3.
 */
interface FeedbackDataSource {
    fun feedback(feedback: Feedback): Observable<CommonResponse>
}