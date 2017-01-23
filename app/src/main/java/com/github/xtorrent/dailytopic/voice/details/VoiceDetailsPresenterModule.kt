package com.github.xtorrent.dailytopic.voice.details

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/23.
 */
@Module
class VoiceDetailsPresenterModule(val view: VoiceDetailsContract.View) {
    @Provides
    fun provideVoiceDetailsContractView(): VoiceDetailsContract.View {
        return view
    }
}