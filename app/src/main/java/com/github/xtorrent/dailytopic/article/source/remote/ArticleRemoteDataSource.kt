package com.github.xtorrent.dailytopic.article.source.remote

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.article.source.ArticleDataSource
import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable
import java.util.*

/**
 * @author Grubber
 */
class ArticleRemoteDataSource : ArticleDataSource {
    override fun getArticle(isRandom: Boolean): Observable<Article> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val url = if (isRandom) "${buildBaseUrl("")}/random" else buildBaseUrl("")
                    val document = newJsoupConnection(url).get()
                    val container = document.getElementById("article_show")
                    val title = container.getElementsByTag("h1").first().text()
                    val author = container.getElementsByClass("article_author").text()
                    val content = container.getElementsByClass("article_text").html()

                    val random = Random()
                    val backgroundImage = "${buildBaseUrl("")}/images/new_feed/bg_${random.nextInt(99)}.jpg"

                    val article = Article(title, author, content, backgroundImage)
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