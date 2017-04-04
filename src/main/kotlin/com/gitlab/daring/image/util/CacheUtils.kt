package com.gitlab.daring.image.util

import com.gitlab.daring.image.util.CommonUtils.closeQuietly
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder

object CacheUtils {

    @JvmStatic
    fun <K, V : AutoCloseable> buildClosableCache(spec: String): Cache<K, V> {
        return CacheBuilder.from(spec)
                .removalListener<K, V> { n -> closeQuietly(n.value) }
                .build<K, V>()
    }

}