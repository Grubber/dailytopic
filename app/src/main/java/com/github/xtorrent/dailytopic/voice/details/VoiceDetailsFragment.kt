package com.github.xtorrent.dailytopic.voice.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.voice.model.Voice

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceDetailsFragment : ContentFragment(), VoiceDetailsContract.View {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_URL = "url"

        fun newInstance(title: String, url: String): VoiceDetailsFragment {
            val fragment = VoiceDetailsFragment()
            val args = Bundle()
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    private val _title by lazy {
        arguments.getString(EXTRA_TITLE)
    }
    private val _url by lazy {
        arguments.getString(EXTRA_URL)
    }

    private lateinit var _presenter: VoiceDetailsContract.Presenter

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_voice_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setUrl(_url)
        _presenter.subscribe()
    }

    override fun setContentView(data: Voice) {
        // TODO
        displayContentView()
    }

    override fun setErrorView() {
        displayErrorView()
    }

    override fun setPresenter(presenter: VoiceDetailsContract.Presenter) {
        _presenter = presenter
    }

    override fun onRetry() {
        _presenter.subscribe()
    }

    override fun onDestroy() {
        _presenter.unsubscribe()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return _title
    }
}