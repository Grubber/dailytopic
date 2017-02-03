package com.github.xtorrent.dailytopic.feedback

import com.github.xtorrent.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/2/3.
 */
@ViewScope
@Subcomponent(modules = arrayOf(FeedbackPresenterModule::class))
interface FeedbackPresenterComponent {
    fun inject(feedbackActivity: FeedbackActivity)
}