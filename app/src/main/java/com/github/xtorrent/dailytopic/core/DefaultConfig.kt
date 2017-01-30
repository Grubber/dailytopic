package com.github.xtorrent.dailytopic.core

/**
 * @author Grubber
 */
fun buildBaseUrl(type: String): String {
    return if (type.isNullOrEmpty()) "https://meiriyiwen.com" else "https://$type.meiriyiwen.com"
}

const val BASE_ENDPOINT = "https://meiriyiwen.com/"