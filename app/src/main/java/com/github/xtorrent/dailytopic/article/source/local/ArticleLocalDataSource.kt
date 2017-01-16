package com.github.xtorrent.dailytopic.article.source.local

import com.github.xtorrent.dailytopic.article.model.Article
import com.github.xtorrent.dailytopic.article.source.ArticleDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.db.model.ArticleModel
import rx.Observable
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable

/**
 * @author Grubber
 */
class ArticleLocalDataSource(private val databaseManager: DatabaseManager) : ArticleDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getCurrentArticle(): Observable<Article> {
        return emptyObservable()
    }

    override fun getArticle(id: Long): Observable<Article> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val query = Article.FACTORY.select_row(id)
                    val cursor = _db.rawQuery(query.statement, query.args)
                    var article: Article? = null
                    while (cursor.moveToNext()) {
                        article = Article.MAPPER.map(cursor)
                    }
                    cursor.close()
                    it.onNext(article)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun saveArticle(article: Article) {
        val insert = ArticleModel.Insert_row(_db)
        insert.bind(article.title(), article.author(), article.content(), article.backgroundImage())
        insert.program.execute()
    }
}