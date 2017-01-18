package com.github.xtorrent.dailytopic.article.source

import com.github.xtorrent.dailytopic.article.source.local.ArticleLocalDataSource
import com.github.xtorrent.dailytopic.article.source.remote.ArticleRemoteDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import dagger.Module
import dagger.Provides

/**
 * @author Grubber
 */
@Module
class ArticleRepositoryModule {
    @MainRepositoryScope
    @Provides
    @LocalArticle
    fun provideArticleLocalDataSource(databaseManager: DatabaseManager): ArticleDataSource {
        return ArticleLocalDataSource(databaseManager)
    }

    @MainRepositoryScope
    @Provides
    @RemoteArticle
    fun provideArticleRemoteDataSource(): ArticleDataSource {
        return ArticleRemoteDataSource()
    }
}