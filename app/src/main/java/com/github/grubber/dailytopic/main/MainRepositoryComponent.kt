package com.github.grubber.dailytopic.main

import com.github.grubber.dailytopic.article.ArticlePresenterComponent
import com.github.grubber.dailytopic.article.ArticlePresenterModule
import com.github.grubber.dailytopic.article.create.CreateArticlePresenterComponent
import com.github.grubber.dailytopic.article.create.CreateArticlePresenterModule
import com.github.grubber.dailytopic.article.source.ArticleRepositoryModule
import com.github.grubber.dailytopic.bookshelf.BookshelfPresenterModule
import com.github.grubber.dailytopic.bookshelf.chapter.ChapterPresenterComponent
import com.github.grubber.dailytopic.bookshelf.chapter.ChapterPresenterModule
import com.github.grubber.dailytopic.bookshelf.details.BookshelfDetailsPresenterComponent
import com.github.grubber.dailytopic.bookshelf.details.BookshelfDetailsPresenterModule
import com.github.grubber.dailytopic.bookshelf.source.BookshelfRepositoryModule
import com.github.grubber.dailytopic.favourite.FavouritePresenterModule
import com.github.grubber.dailytopic.voice.VoicePresenterModule
import com.github.grubber.dailytopic.voice.details.VoiceDetailsPresenterComponent
import com.github.grubber.dailytopic.voice.details.VoiceDetailsPresenterModule
import com.github.grubber.dailytopic.voice.source.VoiceRepositoryModule
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/18.
 */
@MainRepositoryScope
@Subcomponent(modules = arrayOf(ArticleRepositoryModule::class, BookshelfRepositoryModule::class, VoiceRepositoryModule::class))
interface MainRepositoryComponent {
    fun plus(articlePresenterModule: ArticlePresenterModule,
             bookPresenterModule: BookshelfPresenterModule,
             voicePresenterModule: VoicePresenterModule,
             favouritePresenterModule: FavouritePresenterModule): MainPresenterComponent

    fun plus(articlePresenterModule: ArticlePresenterModule): ArticlePresenterComponent

    fun plus(bookshelfDetailsPresenterModule: BookshelfDetailsPresenterModule): BookshelfDetailsPresenterComponent

    fun plus(chapterPresenterModule: ChapterPresenterModule): ChapterPresenterComponent

    fun plus(voiceDetailsPresenterModule: VoiceDetailsPresenterModule): VoiceDetailsPresenterComponent

    fun plus(createArticlePresenterModule: CreateArticlePresenterModule): CreateArticlePresenterComponent
}