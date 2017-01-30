package com.github.xtorrent.dailytopic.article

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.article.source.ArticleRepository
import com.github.xtorrent.dailytopic.utils.applySchedulers
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.utils.plusAssign
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * @author Grubber
 */
class ArticlePresenter @Inject constructor(private val repository: ArticleRepository,
                                           private val view: ArticleContract.View) : ArticleContract.Presenter {
    private val _binder by lazy {
        CompositeSubscription()
    }

    @Inject
    fun setup() {
        view.setPresenter(this)
    }

    private var _isRandom: Boolean = false
    private lateinit var _data: Article
    private var _id: Long = 0

    override fun isRandom(isRandom: Boolean) {
        _isRandom = isRandom
    }

    override fun setId(id: Long) {
        _id = id
    }

    override fun subscribe() {
        _binder.clear()

        val ob = if (_id != 0L) repository.getFavouriteArticle(_id) else repository.getArticle(_isRandom)
        _binder += ob.applySchedulers()
                .bind {
                    next {
                        if (it != null) {
                            _data = it
                            view.setContentView(it)
                        } else {
                            view.setErrorView()
                        }
                    }

                    error {
                        view.setErrorView()
                    }
                }
    }

    override fun isFavourite() {
        _binder += repository.getArticle(_data.title(), _data.author(), Article.Type.FAVOURITE)
                .applySchedulers()
                .bind {
                    next {
                        view.setIsFavourite(it != null)
                    }
                }
    }

    override fun toggleFavourite(isFavourite: Boolean) {
        _binder.clear()

        if (isFavourite) {
            repository.deleteArticle(_data.title(), _data.author(), Article.Type.FAVOURITE)
        } else {
            val persist = Article.create(_data.title(), _data.author(), _data.content(), _data.backgroundImage(), Article.Type.FAVOURITE)
            repository.saveArticle(persist)
        }
        view.setIsFavourite(!isFavourite)
    }

    override fun unsubscribe() {
        _binder.clear()
    }
}