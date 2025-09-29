package nl.rug.ap.a1.observable;
import lombok.NoArgsConstructor;
import nl.rug.ap.a1.ComplianceChecker;
import nl.rug.ap.a1.Trace;
import nl.rug.ap.a1.observer.ProgressObserver;

import java.io.IOException;
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

    // This is a special Trace object used as a signal to tell consumers: "stop processing, no more work coming."
    // It is added to the complete end of the queue.
    private static final Trace POISON_PILL = new Trace("POISON");

    public void startComplianceCheck(Map<String, Trace> traceMap, int nOfThreads){

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
            Thread t = new Thread(() -> processTraces(bQueue));
            t.start();
            consumers.add(t);
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

        observer.reportFinalProgress(); // Notify observer that we are done
    }

    private void loadQueue(BlockingQueue<Trace> bQueue, Map<String, Trace> traceMap){
        for (Trace t : traceMap.values()){
            try {
                bQueue.put(t); // waits if queue is full
            } catch (InterruptedException e) {
                // InterruptedException is caught to handle any unexpected thread interruption (e.g., external
                // signal like ctrl+C). In normal operation with poison pills, this exception is not expected.
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
                checker.check(trace); // check trace status
                observer.updateProgress(trace); // Notify observer about new progress
                observer.reportProgress(); // Change the status

                // Thread.sleep(1000); // Allows to see the actual multithreading
//                synchronized (System.out){
//                    trace.seeTrace(); // Prints out the trace results
//                }
            }
        } catch (InterruptedException e) {
            // InterruptedException is caught to handle any unexpected thread interruption (e.g., external
            // signal like ctrl+C). In normal operation with poison pills, this exception is not expected.
            Thread.currentThread().interrupt();
        }
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
