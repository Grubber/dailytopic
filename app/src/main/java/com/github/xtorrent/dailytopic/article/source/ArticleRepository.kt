package com.github.xtorrent.dailytopic.article.source

import com.github.xtorrent.dailytopic.article.model.Article
import rx.Observable
import javax.inject.Inject

/**
 * @author Grubber
 */
@ArticleScope
class ArticleRepository @Inject constructor(private @LocalArticle val localDataSource: ArticleDataSource,
                                            private @RemoteArticle val remoteDataSource: ArticleDataSource) : ArticleDataSource {
    override fun getArticle(isRandom: Boolean): Observable<Article> {
        return remoteDataSource.getArticle(isRandom)
    }

    override fun getArticle(id: Long): Observable<Article> {
        return localDataSource.getArticle(id)
    }

    override fun saveArticle(article: Article) {
        localDataSource.saveArticle(article)
    }
}