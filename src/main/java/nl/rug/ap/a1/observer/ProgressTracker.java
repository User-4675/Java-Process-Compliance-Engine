package nl.rug.ap.a1.observer;

import java.lang.System;
import lombok.NoArgsConstructor;
import nl.rug.ap.a1.userInterface.ConsoleColor;
import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.traces.TraceStatus;

/**
 * Tracks and reports the progress of trace processing.
 * <p>
 * Implements {@link ProgressObserver} and keeps counters for processed traces,
 * compliant traces, non-compliant traces, and unknown traces.
 * Provides both intermediate and final reporting of progress and statistics.
 */
@NoArgsConstructor
public class ProgressTracker implements ProgressObserver {

    /** Number of traces processed so far. */
    private int processed = 0;

    /** Number of traces marked as compliant. */
    private int compliant = 0;

    /** Number of traces marked as non-compliant. */
    private int nonCompliant = 0;

    /** Number of traces with unknown status. */
    private int unknown = 0;

    /**
     * Updates progress counters after processing a trace.
     *
     * @param t the {@link Trace} that was processed
     */
    @Override
    public synchronized void updateProgress(Trace t) {
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
    public void reportFinalProgress(long start, long end, long totalCheckTime, long maxMemoryUsed) {
        System.out.print("\r" + " ".repeat(80) + "\r"); // Clear line
        System.out.println(
                ConsoleColor.GREEN + "-".repeat(25) + "[ALL CASES PROCESSED]"
                        + "-".repeat(25) + ConsoleColor.RESET);
        System.out.println(
                ConsoleColor.BLUE + "Processed: " + ConsoleColor.RESET + processed
                        + ConsoleColor.GREEN + " Compliant: " + ConsoleColor.RESET + compliant
                        + ConsoleColor.RED + " Non-Compliant: " + ConsoleColor.RESET + nonCompliant
                        + ConsoleColor.YELLOW + " Unknown: " + ConsoleColor.RESET + unknown);

        double maxMemory = maxMemoryUsed / (1024.0 * 1024.0); // MB
        double totalAppRuntime = (end - start) / 1_000_000_000.0; // Seconds
        double avgCheckTime = (processed > 0 ? (totalCheckTime / (double) processed) : 0); // ns

        System.out.printf("Max Consumed Memory: %.3f MB\n", maxMemory);
        System.out.printf("Total log processing time: %.3f s\n", totalAppRuntime);
        System.out.printf("Average case processing time: %.3f ns\n", avgCheckTime);
        System.out.println(ConsoleColor.GREEN + "-".repeat(71) + ConsoleColor.RESET);
    }
}
