package com.github.grubber.dailytopic.feedback

import com.github.grubber.dailytopic.feedback.model.Feedback
import com.github.grubber.dailytopic.feedback.source.FeedbackRepository
import com.github.grubber.dailytopic.utils.applySchedulers
import com.github.grubber.dailytopic.utils.bind
import com.github.grubber.dailytopic.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/2/3.
 */
class FeedbackPresenter @Inject constructor(private val view: FeedbackContract.View,
                                            private val repository: FeedbackRepository) : FeedbackContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    override fun setFeedback(feedback: Feedback) {
        _feedback = feedback
    }

    private lateinit var _feedback: Feedback

    override fun subscribe() {
        _binder.clear()

        _binder += repository.feedback(_feedback)
                .applySchedulers()
                .doOnSubscribe { view.shouldShowLoadingDialog(true) }
                .doOnTerminate { view.shouldShowLoadingDialog(false) }
                .bind {
                    next {
                        if (it != null && !it.objectId.isNullOrBlank()) {
                            view.setContentView(it)
                        } else {
                            view.setErrorView()
                        }
                    }

                    error {
                        view.setErrorView()
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}