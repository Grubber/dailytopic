package com.github.xtorrent.dailytopic.voice.source

import com.github.xtorrent.dailytopic.db.DatabaseManager
import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import com.github.xtorrent.dailytopic.voice.source.local.VoiceLocalDataSource
import com.github.xtorrent.dailytopic.voice.source.remote.VoiceRemoteDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/23.
 */
@Module
class VoiceRepositoryModule {
    @Provides
    @LocalVoice
    @MainRepositoryScope
    fun provideVoiceLocalDataSource(databaseManager: DatabaseManager): VoiceDataSource {
        return VoiceLocalDataSource(databaseManager)
    }

    @Provides
    @RemoteVoice
    @MainRepositoryScope
    fun provideVoiceRemoteDataSource(): VoiceDataSource {
        return VoiceRemoteDataSource()
    }
}