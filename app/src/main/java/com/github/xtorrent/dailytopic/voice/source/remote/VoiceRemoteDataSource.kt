package com.github.xtorrent.dailytopic.voice.source.remote

import android.util.Base64
import com.github.xtorrent.dailytopic.core.buildBaseUrl
import com.github.xtorrent.dailytopic.utils.newJsoupConnection
import com.github.xtorrent.dailytopic.voice.model.Voice
import com.github.xtorrent.dailytopic.voice.source.VoiceDataSource
import rx.Observable
import rx.lang.kotlin.observable
import java.util.regex.Pattern

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
                                val url = it.select("a").first().attr("abs:href")
                                var _id: Long = 0
                                val regex = "(.*)vid=(.*)"
                                val pattern = Pattern.compile(regex)
                                val matcher = pattern.matcher(url)
                                if (matcher.find()) {
                                    _id = matcher.group(2).toLong()
                                }
                                voices += Voice.create(_id, title, author, coverImage, tag, url)
                            }
                    it.onNext(voices)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getVoiceDetails(url: String): Observable<String> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection(url).get()
                    var playUrl: String? = null
                    val srcEncodedText = document.getElementsByTag("embed")
                            .first()
                            .attr("src")
                    val pattern = Pattern.compile("/script/player.swf\\?url=(.*)&autoplay=0")
                    val matcher = pattern.matcher(srcEncodedText)
                    if (matcher.find()) {
                        playUrl = kotlin.text.String(Base64.decode(matcher.group(1), Base64.DEFAULT))
                    }
                    it.onNext(playUrl)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}