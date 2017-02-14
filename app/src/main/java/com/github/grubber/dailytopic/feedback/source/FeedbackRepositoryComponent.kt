package com.github.grubber.dailytopic.feedback.source

import com.github.grubber.dailytopic.feedback.FeedbackPresenterComponent
import com.github.grubber.dailytopic.feedback.FeedbackPresenterModule
import dagger.Subcomponent

/**
 * Created by grubber on 2017/2/3.
 */
@FeedbackScope
@Subcomponent(modules = arrayOf(FeedbackRepositoryModule::class))
interface FeedbackRepositoryComponent {
    fun plus(feedbackPresenterModule: FeedbackPresenterModule): FeedbackPresenterComponent
}