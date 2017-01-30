package com.github.xtorrent.dailytopic.core

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus

/**
 * Created by grubber on 2017/1/6.
 */
object EventBus : Bus() {
    private val _mainThreadHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun post(event: Any?) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event)
        } else {
            _mainThreadHandler.post {
                super.post(event)
            }
        }
    }
}