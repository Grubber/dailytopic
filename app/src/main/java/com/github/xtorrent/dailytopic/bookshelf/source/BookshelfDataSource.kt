package com.github.xtorrent.dailytopic.bookshelf.source

import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import rx.Observable

/**
 * Created by grubber on 2017/1/18.
 */
interface BookshelfDataSource {
    fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Bookshelf>>>

    fun getBookshelfDetails(url: String): Observable<List<Book>>
}