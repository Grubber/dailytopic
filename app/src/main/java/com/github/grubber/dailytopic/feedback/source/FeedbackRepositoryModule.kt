package com.github.grubber.dailytopic.feedback.source

import com.github.grubber.dailytopic.core.di.qualifier.BmobRestfulClient
import com.github.grubber.dailytopic.feedback.service.FeedbackService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Created by grubber on 2017/2/3.
 */
@Module
class FeedbackRepositoryModule {
    @Provides
    @FeedbackScope
    fun provideFeedbackService(@BmobRestfulClient retrofit: Retrofit): FeedbackService {
        return retrofit.create(FeedbackService::class.java)
    }
}