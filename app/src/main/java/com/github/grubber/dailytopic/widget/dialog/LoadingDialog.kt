package com.github.grubber.dailytopic.widget.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import com.github.grubber.dailytopic.R

/**
 * Created by grubber on 2017/2/3.
 */
object LoadingDialog {
    fun create(context: Context): AlertDialog {
        return AlertDialog.Builder(context)
                .setView(R.layout.dialog_loading)
                .create()
    }
}