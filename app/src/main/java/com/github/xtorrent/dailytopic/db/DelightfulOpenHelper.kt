package com.github.xtorrent.dailytopic.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.xtorrent.dailytopic.core.di.qualifier.ForApplication
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import com.github.xtorrent.dailytopic.db.model.ArticleModel
import javax.inject.Inject

/**
 * @author Grubber
 */
@ApplicationScope
class DelightfulOpenHelper @Inject constructor(private @ForApplication val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ArticleModel.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}

const val DB_NAME = "xtorrent.db"
const val DB_VERSION = 1