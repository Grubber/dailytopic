package com.github.grubber.dailytopic.article.source

import com.github.grubber.dailytopic.article.model.Article
import com.github.grubber.dailytopic.main.MainRepositoryScope
import com.github.grubber.dailytopic.utils.bind
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.observable
import javax.inject.Inject

/**
 * @author Grubber
 */
@MainRepositoryScope
class ArticleRepository @Inject constructor(private @LocalArticle val localDataSource: ArticleDataSource,
                                            private @RemoteArticle val remoteDataSource: ArticleDataSource) : ArticleDataSource {
    override fun getArticle(isRandom: Boolean): Observable<Article> {
        return observable { subscriber ->
            if (!subscriber.isUnsubscribed) {
                var article: Article? = null
                val localSubscription = localDataSource.getArticle(isRandom)
                        .subscribe {
                            it?.let {
                                article = it
                                subscriber.onNext(it)
                            }
                        }
                remoteDataSource.getArticle(isRandom)
                        .bind {
                            next {
                                it?.let {
                                    if (isRandom) {
                                        subscriber.onNext(it)
                                    } else {
                                        if (it.title() != article?.title() && it.author() != article?.author()) {
                                            deleteArticle(Article.Type.DAILY)
                                            saveArticle(it)
                                            subscriber.onNext(it)
                                        }
                                    }
                                }
                                subscriber.onCompleted()
                            }

                            error {
                                if (article == null) {
                                    subscriber.onError(it)
                                } else {
                                    subscriber.onCompleted()
                                }
                            }
                        }
                subscriber.add(object : Subscription {
                    override fun isUnsubscribed(): Boolean {
                        return false
                    }

                    override fun unsubscribe() {
                        localSubscription.unsubscribe()
                    }
                })
            }
        }
    }

    override fun getArticle(title: String, author: String, type: Article.Type): Observable<Article> {
        return localDataSource.getArticle(title, author, type)
    }

    override fun deleteArticle(title: String, author: String, type: Article.Type) {
        localDataSource.deleteArticle(title, author, type)
    }

    override fun saveArticle(article: Article) {
        localDataSource.saveArticle(article)
    }

    override fun deleteArticle(type: Article.Type) {
        localDataSource.deleteArticle(type)
    }

    override fun getFavouriteArticleList(): Observable<List<Article>> {
        return localDataSource.getFavouriteArticleList()
    }

    override fun getFavouriteArticle(_id: Long): Observable<Article> {
        return localDataSource.getFavouriteArticle(_id)
    }

    override fun createArticle(title: String, author: String, content: String, deliver: String, source: String): Observable<String> {
        return remoteDataSource.createArticle(title, author, content, deliver, source)
    }
}