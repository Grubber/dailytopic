package com.github.xtorrent.dailytopic.voice.details

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.ContentFragment
import com.github.xtorrent.dailytopic.voice.model.Voice
import com.squareup.picasso.MemoryPolicy

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceDetailsFragment : ContentFragment(), VoiceDetailsContract.View {
    companion object {
        private const val EXTRA_VOICE = "voice"

        fun newInstance(voice: Voice): VoiceDetailsFragment {
            val fragment = VoiceDetailsFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_VOICE, voice)
            fragment.arguments = args
            return fragment
        }
    }

    private val _voice by lazy {
        arguments.getParcelable<Voice>(EXTRA_VOICE)
    }

    private lateinit var _presenter: VoiceDetailsContract.Presenter

    override fun createContentView(container: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_voice_details, container, false)
    }

    private val _backgroundView by bindView<ImageView>(R.id.backgroundView)
    private val _updateView by bindView<ImageView>(R.id.updateView)
    private val _titleView  by bindView<TextView>(R.id.titleView)
    private val _authorView by bindView<TextView>(R.id.authorView)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setUrl(_voice.link())
        _presenter.subscribe()
    }

    override fun setContentView(data: String) {
        // TODO
        picasso().load(_voice.coverImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .fit()
                .into(_backgroundView)
        picasso().load("https://voice.meiriyiwen.com/images/gengxin.png")
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .centerCrop()
                .fit()
                .into(_updateView)
        _titleView.text = _voice.title()
        _authorView.text = _voice.author()
        displayContentView()
    }

    private fun _recycle() {
        picasso().cancelRequest(_backgroundView)
        val backgroundViewDrawable = _backgroundView.drawable
        _backgroundView.setImageDrawable(null)
        val backgroundViewBitmap = (backgroundViewDrawable as? BitmapDrawable)?.bitmap
        backgroundViewBitmap?.recycle()

        picasso().cancelRequest(_updateView)
        val updateViewDrawable = _updateView.drawable
        _updateView.setImageDrawable(null)
        val updateViewBitmap = (updateViewDrawable as? BitmapDrawable)?.bitmap
        updateViewBitmap?.recycle()
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
        _recycle()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return _voice.title()
    }
}