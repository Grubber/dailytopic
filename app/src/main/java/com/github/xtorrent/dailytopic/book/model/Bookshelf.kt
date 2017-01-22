package com.github.xtorrent.dailytopic.book.model

import com.github.xtorrent.dailytopic.db.model.BookshelfModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/18.
 */
@AutoValue
abstract class Bookshelf : BookshelfModel {
    companion object {
        private val _creator: BookshelfModel.Creator<Bookshelf> by lazy {
            BookshelfModel.Creator<Bookshelf>(::AutoValue_Bookshelf)
        }

        val FACTORY = BookshelfModel.Factory<Bookshelf>(_creator)
        val MAPPER = FACTORY.select_rowMapper()

        fun create(title: String, author: String, url: String, image: String): Bookshelf {
            return _creator.create(0, title, author, url, image)
        }
    }
}