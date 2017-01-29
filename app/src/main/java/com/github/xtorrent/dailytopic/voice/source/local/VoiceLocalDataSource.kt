package com.github.xtorrent.dailytopic.voice.source.local

import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.db.model.VoiceModel
import com.github.xtorrent.dailytopic.voice.model.Voice
import com.github.xtorrent.dailytopic.voice.source.VoiceDataSource
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceLocalDataSource(private val databaseManager: DatabaseManager) : VoiceDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getVoiceList(pageNumber: Int): Observable<List<Voice>> {
        // TODO
        return emptyObservable()
    }

    override fun getVoicePlayUrl(url: String): Observable<String> {
        // TODO
        return emptyObservable()
    }

    override fun countVoice(_id: Long): Long {
        var count: Long = 0
        val query = Voice.FACTORY.count_row(_id)
        val cursor = _db.rawQuery(query.statement, query.args)
        while (cursor.moveToNext()) {
            count = Voice.FACTORY.count_rowMapper().map(cursor)
        }
        cursor.close()
        return count
    }

    override fun saveVoice(voice: Voice) {
        val insert = VoiceModel.Insert_row(_db)
        insert.bind(voice._id(), voice.title(), voice.author(), voice.coverImage(), voice.tag(), voice.url())
        insert.program.execute()
    }
}