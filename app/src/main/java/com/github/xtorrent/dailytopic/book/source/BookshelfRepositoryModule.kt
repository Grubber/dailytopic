package com.github.xtorrent.dailytopic.book.source

import com.github.xtorrent.dailytopic.book.source.local.BookshelfLocalDataSource
import com.github.xtorrent.dailytopic.book.source.remote.BookshelfRemoteDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/18.
 */
@Module
class BookshelfRepositoryModule {
    @Provides
    @MainRepositoryScope
    @LocalBookshelf
    fun provideBookshelfLocalDataSource(databaseManager: DatabaseManager): BookshelfDataSource {
        return BookshelfLocalDataSource(databaseManager)
    }

    @Provides
    @MainRepositoryScope
    @RemoteBookshelf
    fun provideBookshelfRemoteDataSource(): BookshelfDataSource {
        return BookshelfRemoteDataSource()
    }
}