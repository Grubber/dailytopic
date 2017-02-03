package com.github.xtorrent.dailytopic.article.source

import com.github.xtorrent.dailytopic.article.create.service.ArticleService
import com.github.xtorrent.dailytopic.article.source.local.ArticleLocalDataSource
import com.github.xtorrent.dailytopic.article.source.remote.ArticleRemoteDataSource
import com.github.xtorrent.dailytopic.core.di.qualifier.AppRestfulClient
import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

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
    fun provideArticleRemoteDataSource(articleService: ArticleService): ArticleDataSource {
        return ArticleRemoteDataSource(articleService)
    }

    @Provides
    @MainRepositoryScope
    fun provideArticleService(@AppRestfulClient retrofit: Retrofit): ArticleService {
        return retrofit.create(ArticleService::class.java)
    }
}