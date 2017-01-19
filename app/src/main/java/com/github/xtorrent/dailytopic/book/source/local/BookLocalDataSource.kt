package com.github.xtorrent.dailytopic.book.source.local

import com.github.xtorrent.dailytopic.book.model.Book
import com.github.xtorrent.dailytopic.book.source.BookDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * Created by grubber on 2017/1/18.
 */
class BookLocalDataSource(private val databaseManager: DatabaseManager) : BookDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getBookList(pageNumber: Int): Observable<List<Book>> {
        // TODO
        return emptyObservable()
    }
}