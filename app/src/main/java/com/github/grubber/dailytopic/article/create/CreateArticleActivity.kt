package com.github.grubber.dailytopic.article.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.grubber.dailytopic.DTApplication
import com.github.grubber.dailytopic.R
import com.github.grubber.dailytopic.base.BaseActivity
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/30.
 */
class CreateArticleActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CreateArticleActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: CreateArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = CreateArticleFragment.newInstance()
        DTApplication.from(this)
                .mainRepositoryComponent
                .plus(CreateArticlePresenterModule(fragment))
                .inject(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}