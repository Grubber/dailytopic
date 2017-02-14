package com.github.grubber.dailytopic.core

/**
 * @author Grubber
 */
fun buildBaseUrl(type: String): String {
    return if (type.isNullOrEmpty()) "https://meiriyiwen.com" else "https://$type.meiriyiwen.com"
}

const val BASE_ENDPOINT = "https://meiriyiwen.com/"

const val BMOB_BASE_ENDPOINT = "https://api.bmob.cn/1/"