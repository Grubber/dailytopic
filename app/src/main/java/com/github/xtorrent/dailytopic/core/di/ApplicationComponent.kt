package com.github.xtorrent.dailytopic.core.di

import com.github.xtorrent.dailytopic.DTApplication
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfRepositoryModule
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import com.github.xtorrent.dailytopic.feedback.source.FeedbackRepositoryComponent
import com.github.xtorrent.dailytopic.feedback.source.FeedbackRepositoryModule
import com.github.xtorrent.dailytopic.main.MainRepositoryComponent
import com.github.xtorrent.dailytopic.voice.source.VoiceRepositoryModule
import dagger.Component

/**
 * @author Grubber
 */
@ApplicationScope
@Component(modules = arrayOf(
        AndroidModule::class,
        DataModule::class,
        NetworkModule::class,
        ApiModule::class,
        UtilsModule::class))
interface ApplicationComponent {
    fun plus(articleRepositoryModule: ArticleRepositoryModule,
             bookRepositoryModule: BookshelfRepositoryModule,
             voiceRepositoryModule: VoiceRepositoryModule): MainRepositoryComponent

    fun plus(feedbackRepositoryModule: FeedbackRepositoryModule): FeedbackRepositoryComponent

    fun inject(application: DTApplication)
}