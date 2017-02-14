package com.github.grubber.dailytopic.utils

import org.jsoup.Connection
import org.jsoup.Jsoup

/**
 * Created by grubber on 2016/12/24.
 */
fun newJsoupConnection(url: String): Connection {
    return Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36")
}