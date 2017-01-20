package com.github.xtorrent.dailytopic.article.source.local

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.article.source.ArticleDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * @author Grubber
 */
class ArticleLocalDataSource(private val databaseManager: DatabaseManager) : ArticleDataSource {
    override fun getArticle(isRandom: Boolean): Observable<Article> {
        return emptyObservable()
    }

    override fun getArticle(id: Long): Observable<Article> {
        return emptyObservable()
    }

    override fun saveArticle(article: Article) {
    }
}