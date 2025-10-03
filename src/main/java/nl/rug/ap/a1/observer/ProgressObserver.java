package nl.rug.ap.a1.observer;

import nl.rug.ap.a1.Trace;

/**
 * Observer interface for monitoring the progress of trace processing.
 * <p>
 * Implementing classes receive updates about individual trace processing
 * and can report intermediate or final progress statistics.
 */
public interface ProgressObserver {

    /**
     * Called when a trace has been processed to update the observer's progress.
     *
     * @param t the {@link Trace} that was processed
     */
    void updateProgress(Trace t);

    /**
     * Reports the current progress of trace processing.
     * <p>
     * This method can be used to display or log intermediate progress information.
     */
    void reportProgress();

    /**
     * Reports the final progress and statistics after all traces have been processed.
     *
     * @param start          the start time of processing in milliseconds
     * @param end            the end time of processing in milliseconds
     * @param threadTime     total thread execution time in milliseconds
     * @param maxMemoryUsed  maximum memory used during processing in bytes
     */
    void reportFinalProgress(long start, long end, long threadTime, long maxMemoryUsed);
}
