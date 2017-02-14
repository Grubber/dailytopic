package com.github.grubber.dailytopic.voice.source

import com.github.grubber.dailytopic.voice.model.Voice
import rx.Observable

/**
 * Created by grubber on 2017/1/23.
 */
interface VoiceDataSource {
    fun getVoiceList(pageNumber: Int): Observable<List<Voice>>
    fun getVoicePlayUrl(url: String): Observable<String>
    fun countVoice(_id: Long): Long
    fun saveVoice(voice: Voice)
    fun updateVoice(url: String, playUrl: String)
}