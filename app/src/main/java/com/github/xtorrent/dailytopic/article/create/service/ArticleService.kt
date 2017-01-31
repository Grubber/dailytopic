package com.github.xtorrent.dailytopic.article.create.service

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * Created by grubber on 2017/1/30.
 */
interface ArticleService {
    @POST("index/add")
    @FormUrlEncoded
    fun create(@Field("title") title: String,
               @Field("author_name") author: String,
               @Field("content") content: String,
               @Field("source_deliverer") deliver: String,
               @Field("source_url") source: String): Observable<String>
}