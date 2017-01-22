package com.github.xtorrent.dailytopic.bookshelf.source.remote

import com.github.xtorrent.dailytopic.bookshelf.model.Bookshelf
import com.github.xtorrent.dailytopic.bookshelf.model.BookshelfHeaderImage
import com.github.xtorrent.dailytopic.bookshelf.source.BookshelfDataSource
import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 2017/1/18.
 */
class BookshelfRemoteDataSource : BookshelfDataSource {
    override fun getBookshelfList(pageNumber: Int): Observable<Pair<List<BookshelfHeaderImage>?, List<Bookshelf>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection(buildBaseUrl("book") + "/book?page=$pageNumber").get()
                    val data = arrayListOf<Bookshelf>()
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
                                data += Bookshelf.create(title, author, url, image)
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