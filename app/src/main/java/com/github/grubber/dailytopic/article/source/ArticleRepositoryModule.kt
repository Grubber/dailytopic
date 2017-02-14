package com.github.grubber.dailytopic.article.source

import com.github.grubber.dailytopic.article.create.service.ArticleService
import com.github.grubber.dailytopic.article.source.local.ArticleLocalDataSource
import com.github.grubber.dailytopic.article.source.remote.ArticleRemoteDataSource
import com.github.grubber.dailytopic.core.di.qualifier.AppRestfulClient
import com.github.grubber.dailytopic.db.DatabaseManager
import com.github.grubber.dailytopic.main.MainRepositoryScope
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