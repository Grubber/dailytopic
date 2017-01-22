package com.github.xtorrent.dailytopic.book.source.remote

import com.github.xtorrent.dailytopic.book.model.Book
import com.github.xtorrent.dailytopic.book.model.BookHeaderImage
import com.github.xtorrent.dailytopic.book.source.BookDataSource
import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 2017/1/18.
 */
class BookRemoteDataSource : BookDataSource {
    override fun getBookList(pageNumber: Int): Observable<Pair<List<BookHeaderImage>?, List<Book>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection(buildBaseUrl("book") + "/book?page=$pageNumber").get()
                    val data = arrayListOf<Book>()
                    val headerImages = arrayListOf<BookHeaderImage>()
                    document.getElementsByClass("slide")[0]
                            .getElementsByTag("a")
                            .forEach {
                                val url = it.attr("href")
                                val image = it.select("img").first().attr("abs:src")
                                headerImages += BookHeaderImage.create(url, image)
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
}