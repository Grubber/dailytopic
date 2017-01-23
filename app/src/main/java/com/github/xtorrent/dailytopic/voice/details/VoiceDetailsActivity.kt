package com.github.xtorrent.dailytopic.voice.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseActivity
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceDetailsActivity : BaseActivity() {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_URL = "url"

        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, VoiceDetailsActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: VoiceDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val url = intent.getStringExtra(EXTRA_URL)
        val fragment = VoiceDetailsFragment.newInstance(title, url)
        DTApplication.from(this)
                .mainRepositoryComponent
                .plus(VoiceDetailsPresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}