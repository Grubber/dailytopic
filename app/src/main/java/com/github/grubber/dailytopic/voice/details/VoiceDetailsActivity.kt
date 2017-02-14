package com.github.grubber.dailytopic.voice.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.grubber.dailytopic.DTApplication
import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.base.BaseActivity
import com.github.grubber.dailytopic.voice.model.Voice
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceDetailsActivity : BaseActivity() {
    companion object {
        private const val EXTRA_VOICE = "voice"

        fun start(context: Context, voice: Voice) {
            val intent = Intent(context, VoiceDetailsActivity::class.java)
            intent.putExtra(EXTRA_VOICE, voice)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: VoiceDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val voice = intent.getParcelableExtra<Voice>(EXTRA_VOICE)
        val fragment = VoiceDetailsFragment.newInstance(voice)
        DTApplication.from(this)
                .mainRepositoryComponent
                .plus(VoiceDetailsPresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}