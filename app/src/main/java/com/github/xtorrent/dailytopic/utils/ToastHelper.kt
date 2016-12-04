package com.github.xtorrent.dailytopic.utils

import android.content.Context
import android.widget.Toast

/**
 * @author Grubber
 */
class ToastHelper(private val context: Context) {
    fun show(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    fun show(resId: Int) = Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
}