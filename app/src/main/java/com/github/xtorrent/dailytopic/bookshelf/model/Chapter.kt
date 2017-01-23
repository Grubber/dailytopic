package com.github.xtorrent.dailytopic.bookshelf.model

import com.github.xtorrent.dailytopic.db.model.ChapterModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/22.
 */
@AutoValue
abstract class Chapter : ChapterModel {
    companion object {
        private val _creator: ChapterModel.Creator<Chapter> by lazy {
            ChapterModel.Creator<Chapter>(::AutoValue_Chapter)
        }

        val FACTORY = ChapterModel.Factory<Chapter>(_creator)

        fun create(title: String, url: String, content: String? = null): Chapter {
            return _creator.create(title, url, content)
        }
    }
}