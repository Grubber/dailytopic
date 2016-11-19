package com.github.xtorrent.dailytopic.main

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.article.ArticleFragment
import com.github.xtorrent.dailytopic.base.BaseFragment
import com.github.xtorrent.dailytopic.book.BookFragment
import com.github.xtorrent.dailytopic.favourite.FavouriteFragment
import com.github.xtorrent.dailytopic.feedback.FeedbackActivity
import com.github.xtorrent.dailytopic.settings.SettingsActivity
import com.github.xtorrent.dailytopic.voice.VoiceFragment

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _setDrawer()
        _setNavigationView()

        _toolbar.setTitle(R.string.drawer_menu_article)
        _replaceContentFrame(ArticleFragment.newInstance())
    }

    override fun onResume() {
        super.onResume()

        _navigationView.setCheckedItem(_checkItemId)
    }

    private fun _setNavigationView() {
        _navigationView.setNavigationItemSelectedListener {
            var content: Fragment? = null

            when (it.itemId) {
                R.id.article -> {
                    _toolbar.setTitle(R.string.drawer_menu_article)
                    content = ArticleFragment.newInstance()
                }

                R.id.voice -> {
                    _toolbar.setTitle(R.string.drawer_menu_voice)
                    content = VoiceFragment.newInstance()
                }

                R.id.book -> {
                    _toolbar.setTitle(R.string.drawer_menu_book)
                    content = BookFragment.newInstance()
                }

                R.id.favourite -> {
                    _toolbar.setTitle(R.string.drawer_menu_favourite)
                    content = FavouriteFragment.newInstance()
                }

                R.id.settings -> SettingsActivity.start(context)

                R.id.feedback -> FeedbackActivity.start(context)
            }

            if (it.itemId != R.id.settings && it.itemId != R.id.feedback && !it.isChecked) {
                content?.let {
                    _replaceContentFrame(it)
                }
                it.isChecked = true
                _checkItemId = it.itemId
            }
            _drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun _replaceContentFrame(content: Fragment) {
        childFragmentManager.beginTransaction()
                .replace(R.id.content, content)
                .commit()
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
        } else {
            (activity as MainActivity).exit()
        }
    }

    override fun getTitle(): String? {
        return null
    }
}