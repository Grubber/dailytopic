package com.github.xtorrent.dailytopic.book.source

import com.github.xtorrent.dailytopic.book.model.Bookshelf
import com.github.xtorrent.dailytopic.book.model.BookshelfHeaderImage
import rx.Observable

/**
 * Created by grubber on 2017/1/18.
 */
interface BookshelfDataSource {
    fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Bookshelf>>>
}