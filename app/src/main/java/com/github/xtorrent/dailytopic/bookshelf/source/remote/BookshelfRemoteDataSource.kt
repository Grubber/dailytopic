package com.github.xtorrent.dailytopic.bookshelf.source.remote

import com.github.xtorrent.dailytopic.bookshelf.model.Book
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.bookshelf.model.Chapter
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfDataSource
import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable
import java.util.regex.Pattern

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
                    document.getElementsByClass("book-list")[0]
                            .getElementsByTag("li")
                            .forEach {
                                val title = it.select("a")[1].text()
                                val url = it.select("a")[1].attr("abs:href")
                                val author = it.getElementsByClass("book-author").first().text()
                                val image = it.select("img").first().attr("abs:src")
                                var _id = 0L
                                val regex = "(.*)bid=(.*)"
                                val pattern = Pattern.compile(regex)
                                val matcher = pattern.matcher(url)
                                if (matcher.find()) {
                                    _id = matcher.group(2).toLong()
                                }
                                data += Book.create(_id, title, author, url, image)
                            }
                    if (pageNumber == 1) {
                        val headerImages = arrayListOf<BookshelfHeaderImage>()
                        document.getElementsByClass("slide")[0]
                                .getElementsByTag("a")
                                .forEach {
                                    val url = it.attr("href")
                                    val image = it.select("img").first().attr("abs:src")
                                    headerImages += BookshelfHeaderImage.create(url, image)
                                }
                        it.onNext(Pair(headerImages, data))
                    } else {
                        it.onNext(Pair(null, data))
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
                    val document = newJsoupConnection(url).get()
                    val book: Book
                    val chapters = arrayListOf<Chapter>()
                    val bookNode = document.getElementsByClass("book-chapter-sidebar")
                            .first()
                    val image = bookNode.select("img").first().attr("abs:src")
                    val title = bookNode.getElementsByClass("book-name").first().text()
                    val author = bookNode.getElementsByClass("book-author").first().text()
                    var _id = 0L
                    val regex = "(.*)bid=(.*)"
                    val pattern = Pattern.compile(regex)
                    val matcher = pattern.matcher(url)
                    if (matcher.find()) {
                        _id = matcher.group(2).toLong()
                    }
                    book = Book.create(_id, title, author, url, image)
                    document.getElementsByClass("chapter-list")
                            .first()
                            .getElementsByTag("a")
                            .forEach {
                                chapters += Chapter.create(it.text(), it.attr("abs:href"), null, _id)
                            }
                    it.onNext(Pair(book, chapters))
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun saveBook(book: Book) {
        // Ignored.
    }

    override fun countBook(url: String): Long {
        // Ignored.
        return 0
    }

    override fun saveBookshelfHeaderImage(bookshelfHeaderImage: BookshelfHeaderImage) {
        // Ignored.
    }

    override fun countBookshelfHeaderImage(url: String): Long {
        // Ignored.
        return 0
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

    override fun countChapter(url: String): Long {
        // Ignored.
        return 0
    }

    override fun saveChapter(chapter: Chapter) {
        // Ignored.
    }

    override fun updateChapter(url: String, content: String) {
        // Ignored.
    }
}