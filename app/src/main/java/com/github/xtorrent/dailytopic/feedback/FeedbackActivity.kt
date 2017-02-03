package com.github.xtorrent.dailytopic.feedback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseActivity
import com.github.xtorrent.dailytopic.feedback.source.FeedbackRepositoryModule
import javax.inject.Inject

/**
 * @author Grubber
 */
class FeedbackActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: FeedbackPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = FeedbackFragment.newInstance()
        DTApplication.from(this)
                .applicationComponent
                .plus(FeedbackRepositoryModule())
                .plus(FeedbackPresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}