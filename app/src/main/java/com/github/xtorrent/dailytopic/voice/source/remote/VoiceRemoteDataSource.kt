package com.github.xtorrent.dailytopic.voice.source.remote

import com.github.xtorrent.dailytopic.voice.model.Voice
import com.github.xtorrent.dailytopic.voice.source.VoiceDataSource
import rx.Observable
import rx.lang.kotlin.emptyObservable

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceRemoteDataSource : VoiceDataSource {
    override fun getVoiceList(pageNumber: Int): Observable<List<Voice>> {
        // TODO
        return emptyObservable()
    }
}