package com.github.grubber.dailytopic.bookshelf.source.local

import com.github.grubber.dailytopic.bookshelf.model.Book
import com.github.grubber.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.grubber.dailytopic.bookshelf.model.Chapter
import com.github.grubber.dailytopic.bookshelf.source.BookshelfDataSource
import com.github.grubber.dailytopic.db.DatabaseManager
import com.github.grubber.dailytopic.db.model.BookModel
import com.github.grubber.dailytopic.db.model.BookshelfHeaderImageModel
import com.github.grubber.dailytopic.db.model.ChapterModel
import rx.Observable
import rx.lang.kotlin.observable
import java.util.regex.Pattern

/**
 * Created by grubber on 2017/1/18.
 */
class BookshelfLocalDataSource(private val databaseManager: DatabaseManager) : BookshelfDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Book>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val books = arrayListOf<Book>()
                    val bookQuery = Book.FACTORY.select_rows(12, (pageNumber - 1) * 12.toLong())
                    val bookCursor = _db.rawQuery(bookQuery.statement, bookQuery.args)
                    bookCursor.use {
                        while (it.moveToNext()) {
                            books += Book.FACTORY.select_rowsMapper().map(it)
                        }
                    }
                    if (pageNumber == 1) {
                        val headerImages = arrayListOf<BookshelfHeaderImage>()
                        val headerImageCursor = _db.rawQuery(BookshelfHeaderImage.FACTORY.select_all().statement, arrayOfNulls(0))
                        headerImageCursor.use {
                            while (it.moveToNext()) {
                                headerImages += BookshelfHeaderImage.FACTORY.select_allMapper().map(it)
                            }
                        }
                        it.onNext(Pair(headerImages, books))
                    } else {
                        it.onNext(Pair(null, books))
                    }
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getBookshelfDetails(url: String): Observable<Pair<Book, List<Chapter>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    var book: Book? = null
                    val chapters = arrayListOf<Chapter>()
                    var _id = 0L
                    val regex = "(.*)bid=(.*)"
                    val pattern = Pattern.compile(regex)
                    val matcher = pattern.matcher(url)
                    if (matcher.find()) {
                        _id = matcher.group(2).toLong()
                    }
                    val query = Chapter.FACTORY.for_book(_id)
                    val cursor = _db.rawQuery(query.statement, query.args)
                    cursor.use {
                        while (it.moveToNext()) {
                            val forBook = Chapter.FOR_BOOK_MAPPER.map(it)
                            if (book == null) {
                                book = forBook.book()
                            }
                            chapters += forBook.chapter()
                        }
                    }
                    it.onNext(Pair(book!!, chapters))
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun saveBook(book: Book) {
        val insert = BookModel.Insert_row(_db)
        insert.bind(book._id(), book.title(), book.author(), book.url(), book.image())
        insert.program.execute()
    }

    override fun countBook(url: String): Long {
        var count = 0L
        val query = Book.FACTORY.count_row(url)
        val cursor = _db.rawQuery(query.statement, query.args)
        cursor.use {
            while (it.moveToNext()) {
                count = Book.FACTORY.count_rowMapper().map(it)
            }
        }
        return count
    }

    override fun saveBookshelfHeaderImage(bookshelfHeaderImage: BookshelfHeaderImage) {
        val insert = BookshelfHeaderImageModel.Insert_row(_db)
        insert.bind(bookshelfHeaderImage.url(), bookshelfHeaderImage.image())
        insert.program.execute()
    }

    override fun countBookshelfHeaderImage(url: String): Long {
        var count = 0L
        val query = BookshelfHeaderImage.FACTORY.count_row(url)
        val cursor = _db.rawQuery(query.statement, query.args)
        cursor.use {
            while (it.moveToNext()) {
                count = BookshelfHeaderImage.FACTORY.count_rowMapper().map(it)
            }
        }
        return count
    }

    override fun getChapter(url: String): Observable<Chapter> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    var data: Chapter? = null
                    val query = Chapter.FACTORY.select_row(url)
                    val cursor = _db.rawQuery(query.statement, query.args)
                    cursor.use {
                        while (it.moveToNext()) {
                            data = Chapter.FACTORY.select_rowMapper().map(it)
                        }
                    }
                    it.onNext(data)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun countChapter(url: String): Long {
        var count = 0L
        val query = Chapter.FACTORY.count_row(url)
        val cursor = _db.rawQuery(query.statement, query.args)
        cursor.use {
            while (it.moveToNext()) {
                count = Chapter.FACTORY.count_rowMapper().map(it)
            }
        }
        return count
    }

    override fun saveChapter(chapter: Chapter) {
        val insert = ChapterModel.Insert_row(_db)
        insert.bind(chapter.title(), chapter.url(), chapter.book())
        insert.program.execute()
    }

    override fun updateChapter(url: String, content: String) {
        val update = ChapterModel.Update_row(_db)
        update.bind(content, url)
        update.program.execute()
    }
}