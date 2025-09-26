package nl.rug.ap.a1;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@NoArgsConstructor
public class ComplianceApp {
    // Flags the Traces based check conditions
    ComplianceChecker checker = new ComplianceChecker();

    // This is a special Trace object used as a signal to tell consumers: "stop processing, no more work coming."
    // It is added to the complete end of the queue.
    private static final Trace POISON_PILL = new Trace("POISON");

    public void startComplianceCheck(Map<String, Trace> traceMap, int nOfThreads){

        // Create a BlockingQueue for Multithreading
        BlockingQueue<Trace> bQueue = new LinkedBlockingQueue<>();

        // Starts the producer Thread
        //   -> Put enqueue all Traces
        //   -> Add poison pill marker at the end of each Trace
        new Thread(() -> {
            // Load the queue with data
            loadQueue(bQueue, traceMap);

            // Add poison pills after all traces are loaded
            // We do this to signal the consumer Threads that we went over whole database.
            for (int i = 0; i < nOfThreads; i++) {
                try {
                    bQueue.put(POISON_PILL);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        // Starts consumer Threads
        for (int i = 0; i < nOfThreads; i++) {
            new Thread(() -> processTraces(bQueue)).start();
        }
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
                if (trace == POISON_PILL) break; // stop signal
                checker.check(trace); // check trace status

                Thread.sleep(1000); // Allows to see the actual multithreading
                synchronized (System.out){
                    trace.seeTrace(); // Prints out the trace results
                }
            }
        } catch (InterruptedException e) {
            // InterruptedException is caught to handle any unexpected thread interruption (e.g., external
            // signal like ctrl+C). In normal operation with poison pills, this exception is not expected.
            Thread.currentThread().interrupt();
        }
    }
}
