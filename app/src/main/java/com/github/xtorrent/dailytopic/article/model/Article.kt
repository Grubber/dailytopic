package com.github.xtorrent.dailytopic.article.model

import com.github.xtorrent.dailytopic.db.model.ArticleModel
import com.google.auto.value.AutoValue

/**
 * @author Grubber
 */
@AutoValue
abstract class Article : ArticleModel {
    companion object {
        private val _creator: ArticleModel.Creator<Article> by lazy {
            ArticleModel.Creator<Article>(::AutoValue_Article)
        }

        val FACTORY = ArticleModel.Factory<Article>(_creator)
        val MAPPER = FACTORY.select_rowMapper()

        fun create(title: String, author: String, content: String, backgroundImage: String): Article {
            return _creator.create(0, title, author, content, backgroundImage)
        }
    }
}