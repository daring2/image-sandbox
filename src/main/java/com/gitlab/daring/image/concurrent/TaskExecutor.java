package com.gitlab.daring.image.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class TaskExecutor implements AutoCloseable {

	final ExecutorService executor;
	final AtomicLong taskIds = new AtomicLong();

	public TaskExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public TaskExecutor() {
		this(newSingleThreadExecutor());
	}

	public void executeAsync(Runnable task) {
		long id = taskIds.incrementAndGet();
		executor.execute(() -> { if (taskIds.get() == id) task.run(); });
	}

	@Override
	public void close() {
		executor.shutdownNow();
	}

}
