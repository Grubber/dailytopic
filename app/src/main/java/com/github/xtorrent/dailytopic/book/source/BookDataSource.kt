package com.github.xtorrent.dailytopic.book.source

import com.github.xtorrent.dailytopic.book.model.Book
import com.github.xtorrent.dailytopic.book.model.BookHeaderImage
import rx.Observable

/**
 * Created by grubber on 2017/1/18.
 */
interface BookDataSource {
    fun getBookList(pageNumber: Int): Observable<Pair<List<BookHeaderImage>?, List<Book>>>
}