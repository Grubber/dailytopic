package com.github.xtorrent.dailytopic.article.source

import com.github.xtorrent.dailytopic.article.source.local.ArticleLocalDataSource
import com.github.xtorrent.dailytopic.article.source.remote.ArticleRemoteDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import dagger.Module
import dagger.Provides

/**
 * @author Grubber
 */
@Module
class ArticleRepositoryModule {
    @ArticleScope
    @Provides
    @LocalArticle
    fun provideArticleLocalDataSource(databaseManager: DatabaseManager): ArticleDataSource {
        return ArticleLocalDataSource(databaseManager)
    }

    @ArticleScope
    @Provides
    @RemoteArticle
    fun provideArticleRemoteDataSource(): ArticleDataSource {
        return ArticleRemoteDataSource()
    }
}