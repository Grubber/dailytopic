package com.github.grubber.dailytopic.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.base.BaseActivity

/**
 * @author Grubber
 */
class SettingsActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, SettingsFragment.newInstance())
                .commit()
    }
}