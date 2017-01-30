package com.github.xtorrent.dailytopic.bookshelf.source.local

import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfDataSource
import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.db.model.ChapterModel
import rx.Observable
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 2017/1/18.
 */
class BookshelfLocalDataSource(private val databaseManager: DatabaseManager) : BookshelfDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Book>>> {
        // TODO
        return emptyObservable()
    }

    override fun getBookshelfDetails(url: String): Observable<Pair<Book, List<Chapter>>> {
        // TODO
        return emptyObservable()
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
        insert.bind(chapter.title(), chapter.url())
        insert.program.execute()
    }

    override fun updateChapter(url: String, content: String) {
        val update = ChapterModel.Update_row(_db)
        update.bind(content, url)
        update.program.execute()
    }
}