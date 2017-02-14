package com.github.grubber.dailytopic.voice.details

import com.github.grubber.dailytopic.utils.applySchedulers
import com.github.grubber.dailytopic.utils.bind
import com.github.grubber.dailytopic.utils.plusAssign
import com.github.grubber.dailytopic.voice.source.VoiceRepository
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceDetailsPresenter @Inject constructor(private val view: VoiceDetailsContract.View,
                                                private val repository: VoiceRepository) : VoiceDetailsContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private lateinit var _url: String

    override fun setUrl(url: String) {
        _url = url
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getVoicePlayUrl(_url)
                .applySchedulers()
                .bind {
                    next {
                        it?.let {
                            view.setContentView(it)
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