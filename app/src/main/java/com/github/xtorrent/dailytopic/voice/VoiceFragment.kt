package com.github.xtorrent.dailytopic.voice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.voice.model.Voice

/**
 * @author Grubber
 */
class VoiceFragment : ContentFragment(), VoiceContract.View {
    companion object {
        fun newInstance(): VoiceFragment {
            return VoiceFragment()
        }
    }

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_voice, container, false)
    }

    private lateinit var _presenter: VoiceContract.Presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setPageNumber(1)
        _presenter.subscribe()
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun setPresenter(presenter: VoiceContract.Presenter) {
        _presenter = presenter
    }

    override fun setContentView(data: List<Voice>) {
        // TODO
        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return null
    }
}