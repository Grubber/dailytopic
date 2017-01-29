package com.github.xtorrent.dailytopic.voice.model

import android.os.Parcelable
import com.github.xtorrent.dailytopic.db.model.VoiceModel
import com.google.auto.value.AutoValue

/**
 * Created by grubber on 2017/1/23.
 */
@AutoValue
abstract class Voice : VoiceModel, Parcelable {
    companion object {
        private val _creator: VoiceModel.Creator<Voice> by lazy {
            VoiceModel.Creator<Voice>(::AutoValue_Voice)
        }

        val FACTORY = VoiceModel.Factory<Voice>(_creator)

        fun create(_id: Long, title: String, author: String, coverImage: String, tag: String, url: String, playUrl: String? = null): Voice {
            return _creator.create(_id, title, author, coverImage, tag, url, playUrl)
        }
    }
}