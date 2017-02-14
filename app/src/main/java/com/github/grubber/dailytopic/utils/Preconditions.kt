package com.github.grubber.dailytopic.utils

/**
 * @author Grubber
 */
fun <T> checkNotNull(t: T?): T {
    return t ?: throw NullPointerException()
}

fun <T> checkNotNull(t: T?, value: Any): T {
    return t ?: throw NullPointerException(value.toString())
}