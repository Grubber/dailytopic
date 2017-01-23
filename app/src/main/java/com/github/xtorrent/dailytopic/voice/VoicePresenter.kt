package com.github.xtorrent.dailytopic.voice

import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.utils.plusAssign
import com.github.xtorrent.dailytopic.voice.source.VoiceRepository
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

    override fun setPageNumber(pageNumber: Int) {
        _pageNumber = pageNumber
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.getVoiceList(_pageNumber)
                .applySchedulers()
                .bind {
                    next {

                    }

                    error {

                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}