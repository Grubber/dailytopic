package com.github.grubber.dailytopic.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.github.grubber.dailytopic.DTApplication
import com.trello.rxlifecycle.components.support.RxFragment
import rx.Observable

/**
 * @author Grubber
 */
abstract class BaseFragment : RxFragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getTitle()?.let {
            (activity as BaseActivity).setTitle(it)
        }
    }

    protected fun toastHelper() = DTApplication.from(context).toastHelper

    protected fun picasso() = DTApplication.from(context).picasso

    protected fun deviceUtils() = DTApplication.from(context).deviceUtils

    protected fun eventBus() = DTApplication.from(context).eventBus

    fun getToolbar(): Toolbar {
        return (activity as BaseActivity).toolbar
    }

    protected fun setTitle(title: String) {
        (activity as BaseActivity).setTitle(title)
    }

    private fun <T> _bindToLifecycle(observable: Observable<T>): Observable<T> {
        return observable.compose(this.bindToLifecycle<T>())
    }

    protected fun <T> bindSubscribe(observable: Observable<T>,
                                    onNext: (T) -> Unit) {
        _bindToLifecycle(observable).subscribe(onNext)
    }

    abstract fun getTitle(): String?
}