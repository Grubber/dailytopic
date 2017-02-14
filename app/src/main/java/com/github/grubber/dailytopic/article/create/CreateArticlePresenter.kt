package com.github.grubber.dailytopic.article.create

import com.github.grubber.dailytopic.article.source.ArticleRepository
import com.github.grubber.dailytopic.utils.applySchedulers
import com.github.grubber.dailytopic.utils.bind
import com.github.grubber.dailytopic.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/30.
 */
class CreateArticlePresenter @Inject constructor(private val view: CreateArticleContract.View,
                                                 private val repository: ArticleRepository) : CreateArticleContract.Presenter {
    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private val _binder by lazy {
        CompositeSubscription()
    }

    private var _title: String = ""
    private var _author: String = ""
    private var _content: String = ""
    private var _deliver: String = ""
    private var _source: String = ""

    override fun setData(title: String, author: String, content: String, deliver: String, source: String) {
        _title = title
        _author = author
        _content = content
        _deliver = deliver
        _source = source
    }

    override fun subscribe() {
        _binder.clear()

        _binder += repository.createArticle(_title, _author, _content, _deliver, _source)
                .applySchedulers()
                .doOnSubscribe { view.showLoadingDialog(true) }
                .doOnTerminate { view.showLoadingDialog(false) }
                .bind {
                    next {
                        it?.let {
                            view.setContentView(it)
                        }
                    }

                    error {
                        view.setErrorView()
                    }
                }
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}