package com.github.grubber.dailytopic.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * Created by grubber on 2017/2/3.
 */
fun getVersionCode(context: Context): Int {
    val pi = _getPackageInfo(context)
    if (pi != null) {
        return pi.versionCode
    }
    return 0
}

fun getVersionName(context: Context): String {
    val pi = _getPackageInfo(context)
    if (pi != null) {
        return pi.versionName
    } else {
        return ""
    }
}

private fun _getPackageInfo(context: Context): PackageInfo? {
    val pm = context.packageManager
    var pi: PackageInfo? = null
    try {
        pi = pm.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
    }
    return pi
}