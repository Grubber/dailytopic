package com.github.xtorrent.dailytopic.article.source

import com.github.xtorrent.dailytopic.article.model.Article
import rx.Observable

/**
 * @author Grubber
 */
interface ArticleDataSource {
    fun getArticle(isRandom: Boolean): Observable<Article>
    fun saveArticle(article: Article)
    fun deleteArticle(type: Article.Type)
    fun getArticle(title: String, author: String, type: Article.Type): Observable<Article>
    fun deleteArticle(title: String, author: String, type: Article.Type)
    fun getFavouriteArticleList(): Observable<List<Article>>
}