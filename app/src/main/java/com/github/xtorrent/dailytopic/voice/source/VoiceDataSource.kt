package com.github.xtorrent.dailytopic.voice.source

import com.github.xtorrent.dailytopic.voice.model.Voice
import rx.Observable

/**
 * Created by grubber on 2017/1/23.
 */
interface VoiceDataSource {
    fun getVoiceList(pageNumber: Int): Observable<List<Voice>>
    fun getVoiceDetails(url: String): Observable<String>
}