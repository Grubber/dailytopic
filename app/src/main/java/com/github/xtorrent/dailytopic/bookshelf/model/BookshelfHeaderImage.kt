package com.github.xtorrent.dailytopic.bookshelf.model

import com.github.xtorrent.dailytopic.db.model.BookshelfHeaderImageModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/22.
 */
@AutoValue
abstract class BookshelfHeaderImage : BookshelfHeaderImageModel {
    companion object {
        private val _creator: BookshelfHeaderImageModel.Creator<BookshelfHeaderImage> by lazy {
            BookshelfHeaderImageModel.Creator<BookshelfHeaderImage>(::AutoValue_BookshelfHeaderImage)
        }

        val FACTORY = BookshelfHeaderImageModel.Factory<BookshelfHeaderImage>(_creator)

        fun create(url: String, image: String): BookshelfHeaderImage {
            return _creator.create(url, image)
        }
    }
}