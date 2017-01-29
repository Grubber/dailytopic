package com.github.xtorrent.dailytopic.voice.details

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
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
import com.github.xtorrent.dailytopic.voice.view.AudioExoPlayerView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
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
    private val _playerView by bindView<AudioExoPlayerView>(R.id.playerView)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _presenter.setUrl(_voice.url())
        _presenter.subscribe()
    }

    override fun setContentView(data: String) {
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
        _playUrl = data
        _initMediaPlayer()
        displayContentView()
    }

    private var _playUrl: String? = null
    private var _exoPlayer: SimpleExoPlayer? = null

    private fun _initMediaPlayer() {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        _exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl)
        _playerView.player = _exoPlayer

        _prepareMediaPlayer()
    }

    private fun _prepareMediaPlayer() {
        val dataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(context, "dailytopic"))
        val extractorsFactory = DefaultExtractorsFactory()
        val videoSource = ExtractorMediaSource(Uri.parse(_playUrl), dataSourceFactory, extractorsFactory, null, null)
        _exoPlayer!!.prepare(videoSource)
    }

    private fun _releaseMediaPlayer() {
        if (_exoPlayer != null) {
            _exoPlayer!!.release()
            _exoPlayer = null
        }
    }

    private fun _recycleBitmap() {
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
        _recycleBitmap()
        _releaseMediaPlayer()
        super.onDestroy()
    }

    override fun getTitle(): String? {
        return _voice.title()
    }
}