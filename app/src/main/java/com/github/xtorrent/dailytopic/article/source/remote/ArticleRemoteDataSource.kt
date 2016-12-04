package com.github.xtorrent.dailytopic.article.source.remote

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.article.source.ArticleDataSource
import com.github.xtorrent.dailytopic.core.BASE_URL
import org.jsoup.Jsoup
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
                    val document = Jsoup.connect(BASE_URL).get()
                    val container = document.getElementsByClass("container").first()
                    val title = container.getElementsByClass("articleTitle").text()
                    val author = container.getElementsByClass("articleAuthorName").text()
                    val content = container.getElementsByClass("articleContent").text()
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