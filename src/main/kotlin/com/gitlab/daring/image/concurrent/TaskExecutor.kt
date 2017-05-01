package com.gitlab.daring.image.concurrent

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.newSingleThreadExecutor
import java.util.concurrent.atomic.AtomicLong

class TaskExecutor (
        val executor: ExecutorService = newSingleThreadExecutor()
) : AutoCloseable {

    private val taskIds = AtomicLong()

    fun executeAsync(task: () -> Unit) {
        val id = taskIds.incrementAndGet()
        executor.execute { if (taskIds.get() == id) task() }
    }

    fun executeAsync(task: Runnable) = executeAsync { task.run() }

    override fun close() {
        executor.shutdownNow()
    }

}