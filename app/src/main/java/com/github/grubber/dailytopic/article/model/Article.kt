package com.github.grubber.dailytopic.article.model

import com.github.grubber.dailytopic.db.model.ArticleModel
import com.google.auto.value.AutoValue
import com.squareup.sqldelight.EnumColumnAdapter

/**
 * @author Grubber
 */
@AutoValue
abstract class Article : ArticleModel {
    enum class Type {
        NONE, DAILY, FAVOURITE
    }

    companion object {
        private val _adapter by lazy {
            EnumColumnAdapter.create(Type::class.java)
        }
        private val _creator: ArticleModel.Creator<Article> by lazy {
            ArticleModel.Creator<Article>(::AutoValue_Article)
        }

        val FACTORY = ArticleModel.Factory<Article>(_creator, _adapter)

        fun create(title: String, author: String, content: String, backgroundImage: String, type: Type): Article {
            return _creator.create(0, title, author, content, backgroundImage, type)
        }
    }
}