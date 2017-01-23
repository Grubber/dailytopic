package com.github.xtorrent.dailytopic.voice.source

import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import com.github.xtorrent.dailytopic.voice.model.Voice
import rx.Observable
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
@MainRepositoryScope
class VoiceRepository @Inject constructor(private @LocalVoice val localDataSource: VoiceDataSource,
                                          private @RemoteVoice val remoteDataSource: VoiceDataSource) : VoiceDataSource {
    override fun getVoiceList(pageNumber: Int): Observable<List<Voice>> {
        return remoteDataSource.getVoiceList(pageNumber)
    }
}