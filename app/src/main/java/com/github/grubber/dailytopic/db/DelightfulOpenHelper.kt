package com.github.grubber.dailytopic.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.github.grubber.dailytopic.core.di.qualifier.ForApplication
import com.github.grubber.dailytopic.core.di.scope.ApplicationScope
import com.github.grubber.dailytopic.db.model.*
import javax.inject.Inject

/**
 * @author Grubber
 */
@ApplicationScope
class DelightfulOpenHelper @Inject constructor(private @ForApplication val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(ArticleModel.CREATE_TABLE)
        db.execSQL(VoiceModel.CREATE_TABLE)
        db.execSQL(BookshelfHeaderImageModel.CREATE_TABLE)
        db.execSQL(BookModel.CREATE_TABLE)
        db.execSQL(ChapterModel.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}

const val DB_NAME = "daily_topic.db"
const val DB_VERSION = 1