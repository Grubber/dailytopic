package com.github.xtorrent.dailytopic.voice.source

import com.github.xtorrent.dailytopic.main.MainRepositoryScope
import com.github.xtorrent.dailytopic.utils.bind
import com.github.xtorrent.dailytopic.voice.model.Voice
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.observable
import javax.inject.Inject

/**
 * Created by grubber on 2017/1/23.
 */
@MainRepositoryScope
class VoiceRepository @Inject constructor(private @LocalVoice val localDataSource: VoiceDataSource,
                                          private @RemoteVoice val remoteDataSource: VoiceDataSource) : VoiceDataSource {
    override fun getVoiceList(pageNumber: Int): Observable<List<Voice>> {
        return observable { subscriber ->
            if (!subscriber.isUnsubscribed) {
                val data = arrayListOf<Voice>()
                val localSubscription = localDataSource.getVoiceList(pageNumber)
                        .bind {
                            next {
                                it?.let {
                                    if (it.isNotEmpty()) {
                                        data += it
                                        subscriber.onNext(it)
                                    }
                                }
                            }
                        }
                remoteDataSource.getVoiceList(pageNumber)
                        .map {
                            it.forEach {
                                val count = countVoice(it._id())
                                if (count == 0L) {
                                    saveVoice(it)
                                }
                            }
                            it
                        }
                        .bind {
                            next {
                                it?.let {
                                    subscriber.onNext(it)
                                    subscriber.onCompleted()
                                }
                            }

                            error {
                                if (data.isEmpty()) {
                                    subscriber.onError(it)
                                }
                            }
                        }
                subscriber.add(object : Subscription {
                    override fun isUnsubscribed(): Boolean {
                        return false
                    }

                    override fun unsubscribe() {
                        localSubscription.unsubscribe()
                    }
                })
            }
        }
    }

    override fun getVoicePlayUrl(url: String): Observable<String> {
        val localResultO = localDataSource.getVoicePlayUrl(url)
        val remoteResultO = remoteDataSource.getVoicePlayUrl(url)
                .map {
                    updateVoice(url, it)
                    it
                }
        return Observable.concat(localResultO, remoteResultO)
                .filter {
                    it != null
                }
                .first()
    }

    override fun countVoice(_id: Long): Long {
        return localDataSource.countVoice(_id)
    }

    override fun saveVoice(voice: Voice) {
        localDataSource.saveVoice(voice)
    }

    override fun updateVoice(url: String, playUrl: String) {
        localDataSource.updateVoice(url, playUrl)
    }
}