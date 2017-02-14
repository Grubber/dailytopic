package com.github.grubber.dailytopic.voice

import com.github.grubber.dailytopic.utils.applySchedulers
import com.github.grubber.dailytopic.utils.bind
import com.github.grubber.dailytopic.utils.plusAssign
import com.github.grubber.dailytopic.voice.model.Voice
import com.github.grubber.dailytopic.voice.source.VoiceRepository
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
class VoicePresenter @Inject constructor(private val view: VoiceContract.View,
                                         private val repository: VoiceRepository) : VoiceContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private var _pageNumber = 1
    private val _data by lazy {
        arrayListOf<Voice>()
    }

    override fun setPageNumber(pageNumber: Int) {
        _pageNumber = pageNumber
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getVoiceList(_pageNumber)
                .applySchedulers()
                .bind {
                    next {
                        if (it != null) {
                            _data.addAll(it)
                            view.setContentView(it, false)
                        }
                    }

                    error {
                        if (_data.isEmpty()) {
                            view.setErrorView()
                        } else {
                            view.setContentView(null, true)
                        }
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}