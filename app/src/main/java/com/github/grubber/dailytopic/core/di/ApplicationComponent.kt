package com.github.grubber.dailytopic.core.di

import com.github.grubber.dailytopic.DTApplication
import com.github.grubber.dailytopic.article.source.ArticleRepositoryModule
import com.github.grubber.dailytopic.bookshelf.source.BookshelfRepositoryModule
import com.github.grubber.dailytopic.core.di.scope.ApplicationScope
import com.github.grubber.dailytopic.feedback.source.FeedbackRepositoryComponent
import com.github.grubber.dailytopic.feedback.source.FeedbackRepositoryModule
import com.github.grubber.dailytopic.main.MainRepositoryComponent
import com.github.grubber.dailytopic.voice.source.VoiceRepositoryModule
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