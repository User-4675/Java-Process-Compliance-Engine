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

@NoArgsConstructor
public class ComplianceApp implements Observable{

    // We only have one observer (The Tracker) thus single variable instead of array
    private ProgressObserver observer;

    // Flags the Traces based check conditions
    ComplianceChecker checker = new ComplianceChecker();

    // This is a special Trace object used to signal to consumers finished processing.
    // It is added to the end of the queue.
    private static final Trace POISON_PILL = new Trace("POISON");

    // Measurement of app performance
    private long totalCheckTime, maxMemoryUsed;

    public void startComplianceCheck(Map<String, Trace> traceMap, int nOfThreads){

        // Track the start of App
        long appStart = System.nanoTime();

        // Create a BlockingQueue for Multithreading
        BlockingQueue<Trace> bQueue = new LinkedBlockingQueue<>();

        // Starts the producer Thread
        //   -> Enqueue all Traces
        //   -> Add poison pill marker at the end of Queue after step 1
        Thread producer = new Thread(() -> {
            loadQueue(bQueue, traceMap);
            for (int i = 0; i < nOfThreads; i++) {
                try {
                    // Add poison pill at the end
                    bQueue.put(POISON_PILL);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();

        // Starts consumer Threads
        List<Thread> consumers = new ArrayList<>();
        for (int i = 0; i < nOfThreads; i++) {
            Thread consumer = new Thread(() -> processTraces(bQueue));
            consumer.start();
            consumers.add(consumer);
        }

        // Wait for all threads to finish and handle exceptions
        // We do this so we can print the final report only after processing all Threads
        try {
            producer.join();
            for (Thread t : consumers) {
                t.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted while waiting for completion.");
        }

        long appEnd = System.nanoTime(); // Track the end of App
        observer.reportFinalProgress(appStart, appEnd, totalCheckTime, maxMemoryUsed);
    }

    private void loadQueue(BlockingQueue<Trace> bQueue, Map<String, Trace> traceMap){
        for (Trace t : traceMap.values()){
            try {
                bQueue.put(t); // waits if queue is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void processTraces(BlockingQueue<Trace> bQueue){
        try {
            while (true) {
                Trace trace = bQueue.take(); // waits if queue is empty
                if (trace == POISON_PILL) break;

                long start = System.nanoTime();
                checker.check(trace); // check trace status
                long end = System.nanoTime();

                recordMetrics(start, end);
                notifyObserver(trace); // Notify observer about new progress
                observer.reportProgress(); // Change the status
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void recordMetrics(long start, long end) {
        // Update total check time
        totalCheckTime += (end - start);

        // Update max memory used
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (usedMemory > maxMemoryUsed) maxMemoryUsed = usedMemory;
    }


    @Override
    public void addObserver(ProgressObserver observer){
        this.observer = observer;
    }

    @Override
    public void notifyObserver(Trace trace){
        observer.updateProgress(trace);
    }
}
