package com.github.xtorrent.dailytopic.bookshelf.model

import android.os.Parcelable
import com.github.xtorrent.dailytopic.db.model.ChapterModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/22.
 */
@AutoValue
abstract class Chapter : ChapterModel, Parcelable {
    @AutoValue
    abstract class ForBook : ChapterModel.For_bookModel<Chapter, Book>

    companion object {
        private val _creator: ChapterModel.Creator<Chapter> by lazy {
            ChapterModel.Creator<Chapter>(::AutoValue_Chapter)
        }

        val FACTORY = ChapterModel.Factory<Chapter>(_creator)

        fun create(title: String, url: String, content: String? = null, book: Long? = 0): Chapter {
            return _creator.create(0, title, url, content, book)
        }

        private val _forBookCreator: ChapterModel.For_bookCreator<Chapter, Book, ForBook> by lazy {
            ChapterModel.For_bookCreator<Chapter, Book, ForBook>(::AutoValue_Chapter_ForBook)
        }
        val FOR_BOOK_MAPPER = FACTORY.for_bookMapper(_forBookCreator, Book.FACTORY)
    }
}