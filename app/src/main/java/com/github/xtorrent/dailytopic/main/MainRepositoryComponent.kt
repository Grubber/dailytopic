package com.github.xtorrent.dailytopic.main

import com.github.xtorrent.dailytopic.article.ArticlePresenterComponent
import com.github.xtorrent.dailytopic.article.ArticlePresenterModule
import com.github.xtorrent.dailytopic.article.source.ArticleRepositoryModule
import com.github.xtorrent.dailytopic.bookshelf.BookshelfPresenterModule
import com.github.xtorrent.dailytopic.bookshelf.chapter.ChapterPresenterComponent
import com.github.xtorrent.dailytopic.bookshelf.chapter.ChapterPresenterModule
import com.github.xtorrent.dailytopic.bookshelf.details.BookshelfDetailsPresenterComponent
import com.github.xtorrent.dailytopic.bookshelf.details.BookshelfDetailsPresenterModule
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfRepositoryModule
import com.github.xtorrent.dailytopic.voice.VoicePresenterModule
import com.github.xtorrent.dailytopic.voice.source.VoiceRepositoryModule
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/18.
 */
@MainRepositoryScope
@Subcomponent(modules = arrayOf(ArticleRepositoryModule::class, BookshelfRepositoryModule::class, VoiceRepositoryModule::class))
interface MainRepositoryComponent {
    fun plus(articlePresenterModule: ArticlePresenterModule,
             bookPresenterModule: BookshelfPresenterModule,
             voicePresenterModule: VoicePresenterModule): MainPresenterComponent

    fun plus(articlePresenterModule: ArticlePresenterModule): ArticlePresenterComponent

    fun plus(bookshelfDetailsPresenterModule: BookshelfDetailsPresenterModule): BookshelfDetailsPresenterComponent

    fun plus(chapterPresenterModule: ChapterPresenterModule): ChapterPresenterComponent
}