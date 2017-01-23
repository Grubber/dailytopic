package com.github.xtorrent.dailytopic.voice.source.remote

import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import com.github.xtorrent.dailytopic.voice.model.Voice
import com.github.xtorrent.dailytopic.voice.source.VoiceDataSource
import rx.Observable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 2017/1/23.
 */
class VoiceRemoteDataSource : VoiceDataSource {
    override fun getVoiceList(pageNumber: Int): Observable<List<Voice>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection("${buildBaseUrl("voice")}/voice/past?page=$pageNumber").get()
                    val voices = arrayListOf<Voice>()
                    document.getElementsByClass("list_box")
                            .forEach {
                                val title = it.getElementsByClass("list_author").first().text()
                                val author = it.getElementsByClass("author_name").first().text()
                                val coverImage = it.select("img").first().attr("abs:src")
                                val tag = it.getElementsByClass("voice_tag").first().text()
                                val link = it.select("a").first().attr("abs:href")
                                voices += Voice.create(title, author, coverImage, tag, link)
                            }
                    it.onNext(voices)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}