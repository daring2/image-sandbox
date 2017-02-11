package com.gitlab.daring.image.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class CollectionUtils {
	
	public static <T, R> List<R> mapList(Collection<T> c, Function<T, R> f) {
		return c.stream().map(f).collect(toList());
	}

	private CollectionUtils() {
	}
	
}