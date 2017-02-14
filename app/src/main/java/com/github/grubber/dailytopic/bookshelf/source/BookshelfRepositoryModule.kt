package com.github.grubber.dailytopic.bookshelf.source

import com.github.grubber.dailytopic.bookshelf.source.local.BookshelfLocalDataSource
import com.github.grubber.dailytopic.bookshelf.source.remote.BookshelfRemoteDataSource
import com.github.grubber.dailytopic.db.DatabaseManager
import com.github.grubber.dailytopic.main.MainRepositoryScope
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