package com.github.grubber.dailytopic.bookshelf.model

import android.os.Parcelable
import com.github.grubber.dailytopic.db.model.BookModel
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

        fun create(_id: Long, title: String, author: String, url: String, image: String): Book {
            return _creator.create(_id, title, author, url, image)
        }
    }
}