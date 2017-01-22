package com.github.xtorrent.dailytopic.book.model

import com.github.xtorrent.dailytopic.db.model.BookHeaderImageModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/22.
 */
@AutoValue
abstract class BookHeaderImage : BookHeaderImageModel {
    companion object {
        private val _creator: BookHeaderImageModel.Creator<BookHeaderImage> by lazy {
            BookHeaderImageModel.Creator<BookHeaderImage>(::AutoValue_BookHeaderImage)
        }

        val FACTORY = BookHeaderImageModel.Factory<BookHeaderImage>(_creator)

        fun create(url: String, image: String): BookHeaderImage {
            return _creator.create(url, image)
        }
    }
}