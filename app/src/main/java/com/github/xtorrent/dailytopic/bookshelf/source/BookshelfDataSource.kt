package com.github.xtorrent.dailytopic.bookshelf.source

import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter
import rx.Observable

/**
 * Created by grubber on 2017/1/18.
 */
interface BookshelfDataSource {
    fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Book>>>
    fun getBookshelfDetails(url: String): Observable<Pair<Book, List<Chapter>>>
    fun saveBook(book: Book)
    fun countBook(url: String): Long

    fun getChapter(url: String): Observable<Chapter>
    fun countChapter(url: String): Long
    fun saveChapter(chapter: Chapter)
    fun updateChapter(url: String, content: String)
}