package com.gitlab.daring.image.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class ValueEvent<T> implements Consumer<T> {

    public boolean enabled = true;
    
    private final List<Consumer<T>> listeners = new CopyOnWriteArrayList<>();

    public void addListener(Consumer<T> l) {
        if (!listeners.contains(l))
            listeners.add(l);
    }

    public void removeListener(Consumer<T> l) {
        listeners.remove(l);
    }

    public void removeListeners() {
        listeners.clear();
    }

    public void fire(T value) {
        if (!enabled) return;
        listeners.forEach(c -> c.accept(value));
    }

    @Override
    public void accept(T v) {
        fire(v);
    }

}
