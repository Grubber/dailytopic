package com.github.xtorrent.dailytopic.article.source.remote

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.article.source.ArticleDataSource
import com.github.xtorrent.dailytopic.core.BASE_URL
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable

/**
 * @author Grubber
 */
class ArticleRemoteDataSource : ArticleDataSource {
    override fun getCurrentArticle(): Observable<Article> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection(BASE_URL).get()
                    val container = document.getElementById("article_show")
                    val title = container.getElementsByTag("h1").first().text()
                    val author = container.getElementsByClass("article_author").text()
                    val content = container.getElementsByClass("article_text").html()
                    val article = Article.create(title, author, content)
                    it.onNext(article)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getArticle(id: Long): Observable<Article> {
        return emptyObservable()
    }

    override fun saveArticle(article: Article) {
        // Ignored.
    }
}