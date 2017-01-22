package com.github.xtorrent.dailytopic.bookshelf.source.local

import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * Created by grubber on 2017/1/18.
 */
class BookshelfLocalDataSource(private val databaseManager: DatabaseManager) : BookshelfDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Bookshelf>>> {
        // TODO
        return emptyObservable()
    }

    override fun getBookshelfDetails(url: String): Observable<List<Book>> {
        // TODO
        return emptyObservable()
    }
}