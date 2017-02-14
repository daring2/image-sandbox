package com.gitlab.daring.image.util;

import one.util.streamex.StreamEx;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CollectionUtils {
	
	public static <T, R> List<R> mapList(Collection<T> c, Function<T, R> f) {
		return StreamEx.of(c).map(f).toList();
	}

	private CollectionUtils() {
	}
	
}