package com.github.xtorrent.dailytopic.base

/**
 * @author Grubber
 */
interface BaseView<in P, in T> {
    fun setPresenter(presenter: P)
    fun setLoadingView()
    fun setErrorView()
    fun setContentView(data: T)
}