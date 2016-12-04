package com.github.xtorrent.dailytopic.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import butterknife.bindView
import com.github.xtorrent.dailytopic.R
import com.github.xtorrent.dailytopic.widget.BetterViewAnimator
import kotlin.properties.Delegates

/**
 * @author Grubber
 */
abstract class ContentFragment : BaseFragment() {
    private val _contentContainer by bindView<FrameLayout>(R.id.contentContainer)
    private val _errorView by bindView<View>(R.id.errorView)

    private var _parentView by Delegates.notNull<BetterViewAnimator>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _parentView = inflater?.inflate(R.layout.fragment_content, container, false) as BetterViewAnimator
        return _parentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayLoadingView()

        val contentView = createContentView(_contentContainer)
        contentView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.CENTER)
        _contentContainer.addView(contentView)

        _errorView.findViewById(R.id.retryButton).setOnClickListener {
            displayLoadingView()
            onRetry()
        }
    }

    abstract fun createContentView(container: ViewGroup): View

    abstract fun onRetry()

    protected fun displayLoadingView() {
        _parentView.setDisplayedChildId(R.id.loadingView)
    }

    protected fun displayContentView() {
        _parentView.setDisplayedChildId(R.id.contentContainer)
    }

    protected fun displayEmptyView() {
        _parentView.setDisplayedChildId(R.id.emptyView)
    }

    protected fun displayErrorView() {
        _parentView.setDisplayedChildId(R.id.errorView)
    }
}