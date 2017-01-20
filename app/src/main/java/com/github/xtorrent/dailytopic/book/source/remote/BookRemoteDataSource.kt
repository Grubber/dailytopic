package com.github.xtorrent.dailytopic.book.source.remote

import com.github.xtorrent.dailytopic.book.model.Book
import com.github.xtorrent.dailytopic.book.source.BookDataSource
import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 2017/1/18.
 */
class BookRemoteDataSource : BookDataSource {
    override fun getBookList(pageNumber: Int): Observable<List<Book>> {
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
                                data += Book(title, author, url, image)
                            }
                    it.onNext(data)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}