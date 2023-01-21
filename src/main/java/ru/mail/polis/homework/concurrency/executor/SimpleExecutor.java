package ru.mail.polis.homework.concurrency.executor;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final int maxThreadCount;
    private volatile boolean isShutDown = false;
    private final AtomicInteger readyThreadsCount;
    private final ConcurrentLinkedQueue<Runnable> commandQueue;
    private final CopyOnWriteArrayList<SimpleThread> threadPool;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.readyThreadsCount = new AtomicInteger(0);
        this.commandQueue = new ConcurrentLinkedQueue<>();
        this.threadPool = new CopyOnWriteArrayList<>();
    }

    private class SimpleThread extends Thread {
        @Override
        public void run() {
            while (!isShutDown) {
                readyThreadsCount.incrementAndGet();
                if (!commandQueue.isEmpty()) {
                    Runnable command = commandQueue.poll();
                    if (command != null) {
                        readyThreadsCount.decrementAndGet();
                        command.run();
                    }
                }
            }
        }
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (isShutDown) {
            throw new RejectedExecutionException();
        } else if (command == null) {
            throw new IllegalArgumentException();
        }
        if (readyThreadsCount.get() == 0 && getLiveThreadsCount() < maxThreadCount) {
            synchronized (this) {
                if (readyThreadsCount.get() == 0 && getLiveThreadsCount() < maxThreadCount) {
                    SimpleThread thread = new SimpleThread();
                    thread.start();
                    threadPool.add(thread);
                }
            }
        }
        commandQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        isShutDown = true;
        threadPool.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadPool.size();
    }
}
