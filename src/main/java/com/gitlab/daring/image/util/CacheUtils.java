package com.gitlab.daring.image.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import static com.gitlab.daring.image.util.CommonUtils.closeQuietly;

public class CacheUtils {

    public static <K, V extends AutoCloseable> Cache<K, V> buildClosableCache(String spec) {
        return CacheBuilder.from(spec)
            .<K, V>removalListener(n -> closeQuietly(n.getValue()))
            .build();
    }

    private CacheUtils() {
    }

}
