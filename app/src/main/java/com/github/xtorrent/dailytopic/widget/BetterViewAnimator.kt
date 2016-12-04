package com.github.xtorrent.dailytopic.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewAnimator

/**
 * @author Grubber
 */
class BetterViewAnimator : ViewAnimator {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    fun setDisplayedChildId(id: Int) {
        if (_getDisplayedChildId() == id) {
            return
        }

        for (i in 0..childCount) {
            if (getChildAt(i).id == id) {
                displayedChild = i
                return
            }
        }

        throw IllegalArgumentException("No view with ID " + id)
    }

    private fun _getDisplayedChildId(): Int {
        return getChildAt(displayedChild).id
    }
}