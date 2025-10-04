package nl.rug.ap.a1.observable;

import lombok.NoArgsConstructor;
import nl.rug.ap.a1.ComplianceChecker;
import nl.rug.ap.a1.Trace;
import nl.rug.ap.a1.observer.ProgressObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Observable application that performs compliance checking on traces.
 * <p>
 * Implements {@link Observable} and follows a producer-consumer pattern to process traces
 * using multiple threads. Notifies a {@link ProgressObserver} about the progress
 * and reports final statistics such as total processing time and memory usage.
 */
@NoArgsConstructor
public class ComplianceApp implements Observable {

    /** The observer that receives progress updates (typically a {@link ProgressObserver}). */
    private ProgressObserver observer;

    /** Checker that evaluates trace compliance. */
    ComplianceChecker checker = new ComplianceChecker();

    /** Special trace used as a marker to signal completion to consumers. */
    private static final Trace POISON_PILL = new Trace("POISON");

    /** Total time spent checking traces (nanoseconds). */
    private long totalCheckTime;

    /** Maximum memory used during processing (bytes). */
    private long maxMemoryUsed;

    /**
     * Starts the compliance checking process on the provided traces using multiple threads.
     * <p>
     * Produces traces into a blocking queue, consumes them concurrently, checks compliance,
     * updates metrics, and notifies the observer about progress.
     *
     * @param traceMap    map of trace IDs to {@link Trace} objects to process
     * @param nOfThreads  number of consumer threads to use
     */
    public void startComplianceCheck(Map<String, Trace> traceMap, int nOfThreads, boolean showProg) {
        long appStart = System.nanoTime();

        BlockingQueue<Trace> bQueue = new LinkedBlockingQueue<>();
        Thread producer = new Thread(() -> {
            loadQueue(bQueue, traceMap);
            for (int i = 0; i < nOfThreads; i++){
                try { bQueue.put(POISON_PILL);}
                catch (InterruptedException e){ Thread.currentThread().interrupt();}
            }
        });
        producer.start();

        List<Thread> consumers = new ArrayList<>();
        for (int i = 0; i < nOfThreads; i++) {
            Thread consumer = new Thread(() -> processTraces(bQueue, showProg));
            consumer.start();
            consumers.add(consumer);
        }

        try {
            producer.join();
            for (Thread t : consumers) {
                t.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted while waiting for completion.");
        }

        long appEnd = System.nanoTime();
        observer.reportFinalProgress(appStart, appEnd, totalCheckTime, maxMemoryUsed);
    }

    /**
     * Loads all traces into the blocking queue for consumers.
     *
     * @param bQueue   the queue to add traces to
     * @param traceMap map of traces to process
     */
    private void loadQueue(BlockingQueue<Trace> bQueue, Map<String, Trace> traceMap) {
        for (Trace t : traceMap.values()) {
            try {
                bQueue.put(t);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Consumes traces from the queue, checks compliance, records metrics,
     * and notifies the observer.
     *
     * @param bQueue the queue to consume traces from
     */
    private void processTraces(BlockingQueue<Trace> bQueue, boolean showProg) {
        try {
            while (true) {
                Trace trace = bQueue.take();
                if (trace == POISON_PILL) break;

                long start = System.nanoTime();
                checker.check(trace);
                long end = System.nanoTime();

                recordMetrics(start, end);
                notifyObserver(trace);
                if (showProg) observer.reportProgress();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Records processing time and updates maximum memory usage.
     *
     * @param start start time in nanoseconds
     * @param end   end time in nanoseconds
     */
    private synchronized void recordMetrics(long start, long end) {
        totalCheckTime += (end - start);

        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (usedMemory > maxMemoryUsed) maxMemoryUsed = usedMemory;
    }

    /**
     * Registers a {@link ProgressObserver} to receive updates.
     *
     * @param observer the observer to add
     */
    @Override
    public void addObserver(ProgressObserver observer) {
        this.observer = observer;
    }

    /**
     * Notifies the registered observer about a processed trace.
     *
     * @param trace the trace that was processed
     */
    @Override
    public void notifyObserver(Trace trace) {
        observer.updateProgress(trace);
    }
}
