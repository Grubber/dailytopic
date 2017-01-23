package com.github.xtorrent.dailytopic.voice.details

import com.github.xtorrent.dailytopic.voice.source.VoiceRepository
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


    }

    override fun unsubscribe() {
        _binder.clear()
    }
}