package com.github.xtorrent.dailytopic.voice.joinus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseActivity

/**
 * Created by grubber on 2017/1/25.
 */
class JoinUsActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, JoinUsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, JoinUsFragment.newInstance())
                .commit()
    }
}