package com.github.grubber.dailytopic.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.grubber.dailytopic.DTApplication
import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.base.BaseActivity
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/16.
 */
class ArticleActivity : BaseActivity() {
    companion object {
        private const val EXTRA_ID = "_id"

        fun start(context: Context, _id: Long = 0) {
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra(EXTRA_ID, _id)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var articlePresenter: ArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val _id = intent.getLongExtra(EXTRA_ID, 0)
        val fragment = ArticleFragment.newInstance(true, _id)
        DTApplication.from(this)
                .mainRepositoryComponent
                .plus(ArticlePresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}