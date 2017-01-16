package com.github.xtorrent.dailytopic.article.random

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.article.ArticleFragment
import com.github.xtorrent.dailytopic.article.ArticlePresenter
import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import com.github.xtorrent.dailytopic.base.BaseActivity
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/16.
 */
class RandomArticleActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RandomArticleActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var articlePresenter: ArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = ArticleFragment.newInstance(true)
        DTApplication.from(this)
                .articleRepositoryComponent
                .plus(ArticlePresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}