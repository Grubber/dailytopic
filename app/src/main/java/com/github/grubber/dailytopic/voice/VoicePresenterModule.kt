package com.github.grubber.dailytopic.voice

import dagger.Module
import dagger.Provides

/**
 * Created by grubber on 2017/1/23.
 */
@Module
class VoicePresenterModule(val view: VoiceContract.View) {
    @Provides
    fun provideVoiceContractView(): VoiceContract.View {
        return view
    }
}