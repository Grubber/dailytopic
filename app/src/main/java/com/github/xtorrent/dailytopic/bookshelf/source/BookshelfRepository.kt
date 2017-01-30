package com.github.xtorrent.dailytopic.bookshelf.source

import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter
import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import rx.Observable
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/18.
 */
@MainRepositoryScope
class BookshelfRepository @Inject constructor(private @LocalBookshelf val localDataSource: BookshelfDataSource,
                                              private @RemoteBookshelf val remoteDataSource: BookshelfDataSource) : BookshelfDataSource {
    override fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Book>>> {
        return remoteDataSource.getBookshelfList(pageNumber)
                .map {
                    it.first?.forEach {
                        val count = countBookshelfHeaderImage(it.url())
                        if (count == 0L) {
                            saveBookshelfHeaderImage(it)
                        }
                    }
                    it.second.forEach {
                        val count = countBook(it.url())
                        if (count == 0L) {
                            saveBook(it)
                        }
                    }
                    it
                }
    }

    override fun getBookshelfDetails(url: String): Observable<Pair<Book, List<Chapter>>> {
        return remoteDataSource.getBookshelfDetails(url)
                .map {
                    it.second.forEach {
                        val count = countChapter(it.url())
                        if (count == 0L) {
                            saveChapter(it)
                        }
                    }
                    it
                }
    }

    override fun countBook(url: String): Long {
        return localDataSource.countBook(url)
    }

    override fun saveBook(book: Book) {
        localDataSource.saveBook(book)
    }

    override fun countBookshelfHeaderImage(url: String): Long {
        return localDataSource.countBookshelfHeaderImage(url)
    }

    override fun saveBookshelfHeaderImage(bookshelfHeaderImage: BookshelfHeaderImage) {
        localDataSource.saveBookshelfHeaderImage(bookshelfHeaderImage)
    }

    override fun getChapter(url: String): Observable<Chapter> {
        val localResultO = localDataSource.getChapter(url)
        val remoteResultO = remoteDataSource.getChapter(url)
                .map {
                    updateChapter(url, it.content()!!)
                    it
                }
        return Observable.concat(localResultO, remoteResultO)
                .filter {
                    it.content() != null
                }
                .first()
    }

    override fun countChapter(url: String): Long {
        return localDataSource.countChapter(url)
    }

    override fun saveChapter(chapter: Chapter) {
        localDataSource.saveChapter(chapter)
    }

    override fun updateChapter(url: String, content: String) {
        localDataSource.updateChapter(url, content)
    }
}