package com.github.xtorrent.dailytopic.voice.source.local

import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.db.model.VoiceModel
import com.github.xtorrent.dailytopic.voice.model.Voice
import com.github.xtorrent.dailytopic.voice.source.VoiceDataSource
import rx.Observable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceLocalDataSource(private val databaseManager: DatabaseManager) : VoiceDataSource {
    private val _db by lazy {
        databaseManager.database
    }

    override fun getVoiceList(pageNumber: Int): Observable<List<Voice>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val data = arrayListOf<Voice>()
                    val query = Voice.FACTORY.select_rows(12, (pageNumber - 1) * 12.toLong())
                    val cursor = _db.rawQuery(query.statement, query.args)
                    cursor.use {
                        while (it.moveToNext()) {
                            data += Voice.FACTORY.select_rowsMapper().map(it)
                        }
                    }
                    it.onNext(data)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getVoicePlayUrl(url: String): Observable<String> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    var playUrl: String? = null
                    val query = Voice.FACTORY.select_row_by_url(url)
                    val cursor = _db.rawQuery(query.statement, query.args)
                    cursor.use {
                        while (it.moveToNext()) {
                            playUrl = Voice.FACTORY.select_row_by_urlMapper().map(it).playUrl()
                        }
                    }
                    it.onNext(playUrl)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun countVoice(_id: Long): Long {
        var count: Long = 0
        val query = Voice.FACTORY.count_row(_id)
        val cursor = _db.rawQuery(query.statement, query.args)
        cursor.use {
            while (it.moveToNext()) {
                count = Voice.FACTORY.count_rowMapper().map(it)
            }
        }
        return count
    }

    override fun saveVoice(voice: Voice) {
        val insert = VoiceModel.Insert_row(_db)
        insert.bind(voice._id(), voice.title(), voice.author(), voice.coverImage(), voice.tag(), voice.url())
        insert.program.execute()
    }

    override fun updateVoice(url: String, playUrl: String) {
        val update = VoiceModel.Update_row(_db)
        update.bind(playUrl, url)
        update.program.execute()
    }
}