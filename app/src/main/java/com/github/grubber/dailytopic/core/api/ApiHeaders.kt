package com.github.grubber.dailytopic.core.api

import com.github.grubber.dailytopic.core.di.scope.ApplicationScope
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by grubber on 2017/2/3.
 */
@ApplicationScope
class ApiHeaders @Inject constructor() : Interceptor {
    private val KEY_CONTENT_TYPE = "Content-Type"
    private val VALUE_CONTENT_TYPE = "application/json"
    private val KEY_APPLICATION_ID = "X-Bmob-Application-Id"
    private val VALUE_APPLICATION_ID = "287fdbe40d79cf128862aa3699154cb2"
    private val KEY_APPLICATION_KEY = "X-Bmob-REST-API-Key"
    private val VALUE_APPLICATION_KEY = "e8aa7a77a58707084155c620b5c18cb0"

    override fun intercept(chain: Interceptor.Chain?): Response? {
        chain?.let {
            val request = chain.request()
            val builder = request.newBuilder()
            builder.addHeader(KEY_CONTENT_TYPE, VALUE_CONTENT_TYPE)
                    .addHeader(KEY_APPLICATION_ID, VALUE_APPLICATION_ID)
                    .addHeader(KEY_APPLICATION_KEY, VALUE_APPLICATION_KEY)
            return chain.proceed(builder.build())
        }
        return null
    }
}