package com.github.grubber.dailytopic.voice.source

import com.github.grubber.dailytopic.db.DatabaseManager
import com.github.grubber.dailytopic.main.MainRepositoryScope
import com.github.grubber.dailytopic.voice.source.local.VoiceLocalDataSource
import com.github.grubber.dailytopic.voice.source.remote.VoiceRemoteDataSource
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