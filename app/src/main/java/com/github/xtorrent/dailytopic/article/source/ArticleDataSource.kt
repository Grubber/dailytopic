package com.github.xtorrent.dailytopic.article.source

import com.github.xtorrent.dailytopic.article.model.Article
import rx.Observable

/**
 * @author Grubber
 */
interface ArticleDataSource {
    fun getCurrentArticle(): Observable<Article>
    fun getArticle(id: Long): Observable<Article>
    fun saveArticle(article: Article)
}