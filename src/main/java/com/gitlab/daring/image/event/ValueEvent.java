package com.gitlab.daring.image.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class ValueEvent<T> {

	private final List<Consumer<T>> listeners = new CopyOnWriteArrayList<>();

	public void addListener(Consumer<T> l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public void removeListener(Consumer<T> l) {
		listeners.remove(l);
	}

	public void fire(T value) {
		listeners.forEach(c -> c.accept(value));
	}

}
