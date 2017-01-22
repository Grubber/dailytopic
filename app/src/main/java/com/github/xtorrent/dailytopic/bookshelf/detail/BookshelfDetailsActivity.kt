package com.github.xtorrent.dailytopic.bookshelf.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.base.BaseActivity
import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/22.
 */
class BookshelfDetailsActivity : BaseActivity() {
    companion object {
        private const val EXTRA_BOOKSHELF = "bookshelf"
        private const val EXTRA_URL = "url"

        fun start(context: Context, bookshelf: Bookshelf?, url: String?) {
            val intent = Intent(context, BookshelfDetailsActivity::class.java)
            bookshelf?.let {
                intent.putExtra(EXTRA_BOOKSHELF, it)
            }
            url?.let {
                intent.putExtra(EXTRA_URL, it)
            }
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: BookshelfDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookshelf = intent.getParcelableExtra<Bookshelf>(EXTRA_BOOKSHELF)
        val url = intent.getStringExtra(EXTRA_URL)
        val fragment = BookshelfDetailsFragment.newInstance(bookshelf, url)
        DTApplication.from(this)
                .mainRepositoryComponent
                .plus(BookshelfDetailsPresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}