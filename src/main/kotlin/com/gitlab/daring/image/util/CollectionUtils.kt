package com.gitlab.daring.image.util

import one.util.streamex.StreamEx
import java.util.function.Function

object CollectionUtils {

    @JvmStatic
    fun <T, R> mapList(c: Collection<T>, f: Function<T, R>): List<R> {
        return StreamEx.of(c).map(f).toList()
    }

}