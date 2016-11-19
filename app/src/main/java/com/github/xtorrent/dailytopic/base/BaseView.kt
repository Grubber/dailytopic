package com.github.xtorrent.dailytopic.base

/**
 * @author Grubber
 */
interface BaseView<in T> {
    fun setPresenter(presenter: T)
}