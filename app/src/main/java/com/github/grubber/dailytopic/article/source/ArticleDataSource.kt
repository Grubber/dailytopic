package com.github.grubber.dailytopic.article.source

import com.github.grubber.dailytopic.article.model.Article
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
    fun getFavouriteArticle(_id: Long): Observable<Article>
    fun createArticle(title: String,
                      author: String,
                      content: String,
                      deliver: String,
                      source: String): Observable<String>
}