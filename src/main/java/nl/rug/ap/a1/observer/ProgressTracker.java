package nl.rug.ap.a1.observer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.rug.ap.a1.userInterface.ConsoleColor;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;

/**
 * Tracks and reports the progress of trace processing.
 * <p>
 * Implements {@link ProgressObserver} and keeps counters for processed traces,
 * compliant traces, non-compliant traces, and unknown traces.
 * Provides both intermediate and final reporting of progress and statistics.
 */
@NoArgsConstructor
@Getter
public class ProgressTracker implements ProgressObserver {

    /** Number of traces processed so far. */
    private int processed = 0;

    /** Number of traces marked as compliant. */
    private int compliant = 0;

    /** Number of traces marked as non-compliant. */
    private int nonCompliant = 0;

    /** Number of traces with unknown status. */
    private int unknown = 0;

    /** Maximum memory consumed in Mega Bytes. */
    private double maxMemoryMB;

    /** Total runtime in seconds. */
    private double totalRunTimeSec;

    /** Average check time in nanoseconds. */
    private double avgCheckTimeNs;

    /** Number of dashes1.*/
    private final int dashes1 = 25;

    /** Number of dashes2.*/
    private final int dashes2 = 71;

    /** Number of spaces. */
    private final int spaces = 80;

    /** Number of bytes.*/
    private final double bytes = 1024.0;

    /** Number of ns in seconds. */
    private final double nanoseconds = 1_000_000_000.0;

    /**
     * Updates progress counters after processing a trace.
     *
     * @param t the {@link Trace} that was processed
     */
    @Override
    public synchronized void updateProgress(final Trace t) {
        processed++;
        if (t.getStatus() == TraceStatus.UNKNOWN) unknown++;
        else if (t.getStatus() == TraceStatus.COMPLIANT) compliant++;
        else nonCompliant++;
    }

    /**
     * Reports the current progress of trace processing.
     * <p>
     * Prints the counts of processed, compliant, non-compliant, and unknown traces
     * to the console.
     */
    @Override
    public void reportProgress() {
        System.out.printf(
                "\rProcessed: %d Compliant: %d Non-compliant: %d Unknown: %d    ",
                processed, compliant, nonCompliant, unknown);
        System.out.flush();
    }

    /**
     * Reports the final progress and statistics after all traces have been processed.
     * <p>
     * Prints a summary to the console, including memory usage, total runtime,
     * and average processing time per trace.
     *
     * @param start          start time of processing in nanoseconds
     * @param end            end time of processing in nanoseconds
     * @param totalCheckTime total time spent checking traces in nanoseconds
     * @param maxMemoryUsed  maximum memory used in bytes
     */
    @Override
    public void reportFinalProgress(final long start, final long end, final long totalCheckTime, final long maxMemoryUsed) {
        System.out.print("\r" + " ".repeat(spaces) + "\r"); // Clear line
        System.out.println(
                ConsoleColor.GREEN + "-".repeat(dashes1) + "[ALL CASES PROCESSED]"
                        + "-".repeat(dashes1) + ConsoleColor.RESET);
        System.out.println(
                ConsoleColor.BLUE + "Processed: " + ConsoleColor.RESET + processed
                        + ConsoleColor.GREEN + " Compliant: " + ConsoleColor.RESET + compliant
                        + ConsoleColor.RED + " Non-Compliant: " + ConsoleColor.RESET + nonCompliant
                        + ConsoleColor.YELLOW + " Unknown: " + ConsoleColor.RESET + unknown);

        this.maxMemoryMB = maxMemoryUsed / (bytes * bytes);
        this.totalRunTimeSec = (end - start) / nanoseconds;
        this.avgCheckTimeNs = (processed > 0 ? (totalCheckTime / (double) processed) : 0);

        System.out.printf("Max Consumed Memory: %.3f MB\n", this.maxMemoryMB);
        System.out.printf("Total log processing time: %.3f s\n", this.totalRunTimeSec);
        System.out.printf("Average case processing time: %.3f ns\n", this.avgCheckTimeNs);
        System.out.println(ConsoleColor.GREEN + "-".repeat(dashes2) + ConsoleColor.RESET);
    }
}
