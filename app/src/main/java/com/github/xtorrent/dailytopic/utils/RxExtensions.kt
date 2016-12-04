package com.github.xtorrent.dailytopic.utils

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * @author Grubber
 */
fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applyToIOScheduler(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.applyToMainThread(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

operator fun CompositeSubscription.plusAssign(subscription: Subscription) {
    this.add(subscription)
}

fun <T> Observable<T>.bind(init: Observer<T>.() -> Unit): Subscription {
    return this.subscribe(Observer(init))
}

class Observer<T>(init: Observer<T>.() -> Unit) : rx.Observer<T> {
    private var _next: ((T?) -> Unit)? = null
    private var _completed: (() -> Unit)? = null
    private var _error: ((Throwable?) -> Unit)? = null

    init {
        init(this)
    }

    override fun onNext(t: T?) {
        _next?.invoke(t)
    }

    override fun onCompleted() {
        _completed?.invoke()
    }

    override fun onError(e: Throwable?) {
        _error?.invoke(e)
    }

    fun next(next: (T?) -> Unit) {
        _next = next
    }

    fun completed(completed: () -> Unit) {
        _completed = completed
    }

    fun error(error: (Throwable?) -> Unit) {
        _error = error
    }
}