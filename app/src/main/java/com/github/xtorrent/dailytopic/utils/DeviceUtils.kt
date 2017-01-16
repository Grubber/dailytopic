package com.github.xtorrent.dailytopic.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by grubber on 2017/1/17.
 */
class DeviceUtils(private val context: Context) {
    fun getScreenSize(): Point {
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val outSize = Point()
        display.getSize(outSize)
        return outSize
    }
}