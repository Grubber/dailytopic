package com.github.xtorrent.dailytopic.voice.details

import com.github.xtorrent.dailytopic.core.di.scope.ViewScope
import dagger.Subcomponent

/**
 * Created by grubber on 2017/1/23.
 */
@ViewScope
@Subcomponent(modules = arrayOf(VoiceDetailsPresenterModule::class))
interface VoiceDetailsPresenterComponent {
    fun inject(voiceDetailsActivity: VoiceDetailsActivity)
}