package com.github.xtorrent.dailytopic.main

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.article.ArticleFragment
import com.github.xtorrent.dailytopic.article.ArticlePresenter
import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import com.github.xtorrent.dailytopic.base.BaseFragment
import com.github.xtorrent.dailytopic.book.BookFragment
import com.github.xtorrent.dailytopic.favourite.FavouriteFragment
import com.github.xtorrent.dailytopic.feedback.FeedbackActivity
import com.github.xtorrent.dailytopic.settings.SettingsActivity
import com.github.xtorrent.dailytopic.voice.VoiceFragment
import javax.inject.Inject

/**
 * @author Grubber
 */
class MainFragment : BaseFragment() {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    private val _toolbar by bindView<Toolbar>(R.id.toolbar)
    private val _drawerLayout by bindView<DrawerLayout>(R.id.drawerLayout)
    private val _navigationView by bindView<NavigationView>(R.id.navigationView)

    private var _drawerToggle: ActionBarDrawerToggle? = null
    private var _checkItemId: Int = R.id.article

    @Inject
    lateinit var articlePresenter: ArticlePresenter

    private val _articleFragment by lazy {
        ArticleFragment.newInstance()
    }
    private val _voiceFragment by lazy {
        VoiceFragment.newInstance()
    }
    private val _bookFragment by  lazy {
        BookFragment.newInstance()
    }
    private val _favouriteFragment by lazy {
        FavouriteFragment.newInstance()
    }
    private val _fragments by lazy {
        arrayListOf(
                _articleFragment,
                _voiceFragment,
                _bookFragment,
                _favouriteFragment
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DTApplication.from(context)
                .articleRepositoryComponent
                .plus(ArticlePresenterModule(_articleFragment))
                .inject(this)

        _setDrawer()
        _setNavigationView()

        _toolbar.setTitle(R.string.drawer_menu_article)
        _initFragments()
        _showContentFrame(0)
    }

    override fun onResume() {
        super.onResume()

        _navigationView.setCheckedItem(_checkItemId)
    }

    private fun _setNavigationView() {
        _navigationView.setNavigationItemSelectedListener {
            var index: Int = 0

            when (it.itemId) {
                R.id.article -> {
                    _toolbar.setTitle(R.string.drawer_menu_article)
                    index = 0
                }

                R.id.voice -> {
                    _toolbar.setTitle(R.string.drawer_menu_voice)
                    index = 1
                }

                R.id.book -> {
                    _toolbar.setTitle(R.string.drawer_menu_book)
                    index = 2
                }

                R.id.favourite -> {
                    _toolbar.setTitle(R.string.drawer_menu_favourite)
                    index = 3
                }

                R.id.settings -> SettingsActivity.start(context)

                R.id.feedback -> FeedbackActivity.start(context)
            }

            if (it.itemId != R.id.settings && it.itemId != R.id.feedback && !it.isChecked) {
                _showContentFrame(index)
                it.isChecked = true
                _checkItemId = it.itemId
            }
            _drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun _initFragments() {
        val transaction = childFragmentManager.beginTransaction()
        _fragments.forEach {
            transaction.add(R.id.content, it)
        }
        transaction.commit()
    }

    private fun _showContentFrame(index: Int) {
        val transaction = childFragmentManager.beginTransaction()
        _fragments.forEachIndexed { i, fragment ->
            if (i == index) {
                transaction.show(fragment)
            } else {
                transaction.hide(fragment)
            }
        }
        transaction.commit()
    }

    private fun _setDrawer() {
        _drawerToggle = object : ActionBarDrawerToggle(activity, _drawerLayout, _toolbar,
                R.string.drawer_open, R.string.drawer_close) {}
        _drawerLayout.addDrawerListener(_drawerToggle!!)
        _drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        _drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        _drawerToggle?.let {
            if (it.onOptionsItemSelected(item)) return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun onBackPressed() {
        if (_drawerLayout.isDrawerOpen(GravityCompat.START)) {
            _drawerLayout.closeDrawer(GravityCompat.START)
        } else if (_checkItemId != R.id.article) {
            _showContentFrame(0)
            _toolbar.setTitle(R.string.drawer_menu_article)
            _checkItemId = R.id.article
            _navigationView.setCheckedItem(_checkItemId)
        } else {
            (activity as MainActivity).exit()
        }
    }

    override fun getTitle(): String? {
        return null
    }
}