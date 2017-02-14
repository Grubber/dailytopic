package com.github.grubber.dailytopic.feedback

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/2/3.
 */
@Module
class FeedbackPresenterModule(val view: FeedbackContract.View) {
    @Provides
    fun provideFeedbackContractView(): FeedbackContract.View {
        return view
    }
}