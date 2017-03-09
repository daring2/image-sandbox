package com.gitlab.daring.image;

import com.gitlab.daring.image.event.VoidEvent;

public class MainContext implements AutoCloseable {

    static final MainContext Instance = new MainContext();

    public static MainContext mainContext() {
        return Instance;
    }

    public final VoidEvent closeEvent = new VoidEvent();

    public void close() {
        closeEvent.fire();
    }

    private MainContext() {
    }

}
