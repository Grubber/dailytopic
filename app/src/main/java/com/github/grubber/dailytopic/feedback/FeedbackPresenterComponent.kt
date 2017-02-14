package com.github.grubber.dailytopic.feedback

import com.github.grubber.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/2/3.
 */
@ViewScope
@Subcomponent(modules = arrayOf(FeedbackPresenterModule::class))
interface FeedbackPresenterComponent {
    fun inject(feedbackActivity: FeedbackActivity)
}