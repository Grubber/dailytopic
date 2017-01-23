package com.github.xtorrent.dailytopic.bookshelf.source.remote

import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfDataSource
import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 2017/1/18.
 */
class BookshelfRemoteDataSource : BookshelfDataSource {
    override fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Book>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection(buildBaseUrl("book") + "/book?page=$pageNumber").get()
                    val data = arrayListOf<Book>()
                    val headerImages = arrayListOf<BookshelfHeaderImage>()
                    document.getElementsByClass("slide")[0]
                            .getElementsByTag("a")
                            .forEach {
                                val url = it.attr("href")
                                val image = it.select("img").first().attr("abs:src")
                                headerImages += BookshelfHeaderImage.create(url, image)
                            }
                    document.getElementsByClass("book-list")[0]
                            .getElementsByTag("li")
                            .forEach {
                                val title = it.select("a")[1].text()
                                val url = it.select("a")[1].attr("abs:href")
                                val author = it.getElementsByClass("book-author").first().text()
                                val image = it.select("img").first().attr("abs:src")
                                data += Book.create(title, author, url, image)
                            }
                    it.onNext(if (pageNumber == 1) Pair(headerImages, data) else Pair(null, data))
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
                    val document = newJsoupConnection(url).get()
                    val book: Book
                    val chapters = arrayListOf<Chapter>()
                    val bookNode = document.getElementsByClass("book-chapter-sidebar")
                            .first()
                    val image = bookNode.select("img").first().attr("abs:src")
                    val title = bookNode.getElementsByClass("book-name").first().text()
                    val author = bookNode.getElementsByClass("book-author").first().text()
                    book = Book.create(title, author, url, image)
                    document.getElementsByClass("chapter-list")
                            .first()
                            .getElementsByTag("a")
                            .forEach {
                                chapters += Chapter.create(it.text(), it.attr("abs:href"))
                            }
                    it.onNext(Pair(book, chapters))
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getChapter(url: String): Observable<Chapter> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection(url).get()
                    val title = document.getElementsByClass("list-header").text().trim()
                    val content = document.getElementsByClass("chapter-bg").first().html()
                    it.onNext(Chapter.create(title, url, content))
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}