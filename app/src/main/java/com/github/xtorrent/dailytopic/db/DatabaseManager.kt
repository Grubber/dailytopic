package com.github.xtorrent.dailytopic.db

import android.database.sqlite.SQLiteDatabase
import com.github.xtorrent.dailytopic.core.di.scope.ApplicationScope
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * @author Grubber
 */
@ApplicationScope
class DatabaseManager @Inject constructor(private val helper: DelightfulOpenHelper) {
    private val _openCount by lazy {
        AtomicInteger()
    }

    var database by Delegates.notNull<SQLiteDatabase>()

    @Synchronized fun open(): SQLiteDatabase {
        if (_openCount.incrementAndGet() == 1) {
            database = helper.writableDatabase
        }
        return database
    }

    @Synchronized fun close() {
        if (_openCount.decrementAndGet() == 0) {
            database.close()
        }
    }
}