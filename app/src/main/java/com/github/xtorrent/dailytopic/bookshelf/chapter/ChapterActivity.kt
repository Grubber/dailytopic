package com.github.xtorrent.dailytopic.bookshelf.chapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseActivity

/**
 * Created by grubber on 2017/1/23.
 */
class ChapterActivity : BaseActivity() {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_URL = "url"

        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, ChapterActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val url = intent.getStringExtra(EXTRA_URL)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, ChapterFragment.newInstance(title, url))
                .commit()
    }
}