package com.github.xtorrent.dailytopic.bookshelf.chapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseActivity
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter
import java.util.*
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
class ChapterActivity : BaseActivity() {
    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_DATA = "data"
        private const val EXTRA_INDEX = "index"

        fun start(context: Context, title: String, data: ArrayList<Chapter>, index: Int) {
            val intent = Intent(context, ChapterActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putParcelableArrayListExtra(EXTRA_DATA, data)
            intent.putExtra(EXTRA_INDEX, index)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: ChapterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val data = intent.getParcelableArrayListExtra<Chapter>(EXTRA_DATA)
        val index = intent.getIntExtra(EXTRA_INDEX, 0)
        val fragment = ChapterFragment.newInstance(title, data, index)
        DTApplication.from(this)
                .mainRepositoryComponent
                .plus(ChapterPresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}