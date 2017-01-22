package com.github.xtorrent.dailytopic.bookshelf.model

import com.github.xtorrent.dailytopic.db.model.BookModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/22.
 */
@AutoValue
abstract class Book : BookModel {
    companion object {
        private val _creator: BookModel.Creator<Book> by lazy {
            BookModel.Creator<Book>(::AutoValue_Book)
        }

        val FACTORY = BookModel.Factory<Book>(_creator)

        fun create(title: String, url: String): Book {
            return _creator.create(title, url)
        }
    }
}