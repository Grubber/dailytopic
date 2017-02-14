package com.github.grubber.dailytopic.voice.details

import com.github.grubber.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/23.
 */
@ViewScope
@Subcomponent(modules = arrayOf(VoiceDetailsPresenterModule::class))
interface VoiceDetailsPresenterComponent {
    fun inject(voiceDetailsActivity: VoiceDetailsActivity)
}