package com.github.grubber.dailytopic.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import butterknife.bindView
import com.bugtags.library.Bugtags
import com.github.grubber.dailytopic.R
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

/**
 * @author Grubber
 */
abstract class BaseActivity : RxAppCompatActivity() {
    val toolbar by bindView<Toolbar>(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_content)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onResume() {
        super.onResume()
        Bugtags.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        Bugtags.onPause(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Bugtags.onDispatchTouchEvent(this, ev)
        return super.dispatchTouchEvent(ev)
    }
}