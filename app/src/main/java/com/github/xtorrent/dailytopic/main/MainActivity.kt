package com.github.xtorrent.dailytopic.main

import android.os.Bundle
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseActivity

/**
 * @author Grubber
 */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideToolbar()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, MainFragment.newInstance())
                .commit()
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.content) as MainFragment).onBackPressed()
    }

    fun exit() {
        super.onBackPressed()
    }
}