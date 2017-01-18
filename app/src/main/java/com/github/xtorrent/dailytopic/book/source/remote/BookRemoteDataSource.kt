package com.github.xtorrent.dailytopic.book.source.remote

import com.github.xtorrent.dailytopic.book.model.Book
import com.github.xtorrent.dailytopic.book.source.BookDataSource
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * Created by grubber on 2017/1/18.
 */
class BookRemoteDataSource : BookDataSource {
    override fun getBookList(): Observable<List<Book>> {
        // TODO
        return emptyObservable()
    }
}