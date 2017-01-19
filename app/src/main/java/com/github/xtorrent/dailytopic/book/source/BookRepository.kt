package com.github.xtorrent.dailytopic.book.source

import com.github.xtorrent.dailytopic.book.model.Book
import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import rx.Observable
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/18.
 */
@MainRepositoryScope
class BookRepository @Inject constructor(private @LocalBook val localDataSource: BookDataSource,
                                         private @RemoteBook val remoteDataSource: BookDataSource) : BookDataSource {
    override fun getBookList(pageNumber: Int): Observable<List<Book>> {
        return remoteDataSource.getBookList(pageNumber)
    }
}