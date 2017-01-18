package com.github.xtorrent.dailytopic.book.source

import com.github.xtorrent.dailytopic.book.source.local.BookLocalDataSource
import com.github.xtorrent.dailytopic.book.source.remote.BookRemoteDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/18.
 */
@Module
class BookRepositoryModule {
    @Provides
    @BookScope
    @LocalBook
    fun provideBookLocalDataSource(databaseManager: DatabaseManager): BookDataSource {
        return BookLocalDataSource(databaseManager)
    }

    @Provides
    @BookScope
    @RemoteBook
    fun provideBookRemoteDataSource(): BookDataSource {
        return BookRemoteDataSource()
    }
}