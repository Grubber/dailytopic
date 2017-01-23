package com.github.xtorrent.dailytopic.bookshelf.model

import android.os.Parcelable
import com.github.xtorrent.dailytopic.db.model.BookModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/18.
 */
@AutoValue
abstract class Book : BookModel, Parcelable {
    companion object {
        private val _creator: BookModel.Creator<Book> by lazy {
            BookModel.Creator<Book>(::AutoValue_Book)
        }

        val FACTORY = BookModel.Factory<Book>(_creator)
        val MAPPER = FACTORY.select_rowMapper()

        fun create(title: String, author: String, url: String, image: String): Book {
            return _creator.create(0, title, author, url, image)
        }
    }
}