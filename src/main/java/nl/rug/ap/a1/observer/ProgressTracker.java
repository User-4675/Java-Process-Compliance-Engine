package nl.rug.ap.a1.observer;

import java.lang.System;
import java.util.HashMap;

import lombok.NoArgsConstructor;
import nl.rug.ap.a1.ConsoleColor;
import nl.rug.ap.a1.Trace;
import nl.rug.ap.a1.TraceStatus;

@NoArgsConstructor
public class ProgressTracker implements ProgressObserver {

    // These variables keep track of progress done by App
    private int processed = 0, compliant = 0, nonCompliant = 0, unknown = 0;

    // This function updates tracker after every processed trace
    @Override
    public synchronized void updateProgress(Trace t){
        processed++;
        if (t.getStatus() == TraceStatus.UNKNOWN) unknown++;
        else if (t.getStatus() == TraceStatus.COMPLIANT) compliant++;
        else nonCompliant++;
    }

    @Override
    public void reportProgress(){
        System.out.printf(
                "\rProcessed: %d Compliant: %d Non-compliant: %d Unknown: %d    ",
                processed, compliant, nonCompliant, unknown);
        System.out.flush();
    }

    @Override
    public void reportFinalProgress(long start, long end, long totalCheckTime, long maxMemoryUsed){
        System.out.print("\r" + " ".repeat(80) + "\r"); // Adjust width if necessary
        System.out.println(
                ConsoleColor.GREEN + "-".repeat(25) + "[ALL CASES PROCESSED]"
                + "-".repeat(25) + ConsoleColor.RESET);
        System.out.println(
                ConsoleColor.BLUE + "Processed: " + ConsoleColor.RESET + processed
                + ConsoleColor.GREEN + " Compliant: " + ConsoleColor.RESET + compliant
                + ConsoleColor.RED + " Non-Compliant: " + ConsoleColor.RESET + nonCompliant
                + ConsoleColor.YELLOW + " Unknown: " + ConsoleColor.RESET + unknown);

        double maxMemory = (maxMemoryUsed) / (1024.0 * 1024.0); // Megabytes
        double totalAppRuntime = (end - start) / 1_000_000_000.0; // Seconds
        double avgCheckTime = (processed > 0 ? (totalCheckTime / (double)processed) : 0); // Nanoseconds

        System.out.printf("Max Consumed Memory: %.3f MB\n", maxMemory);
        System.out.printf("Total log processing time: %.3f s\n", totalAppRuntime);
        System.out.printf("Average case processing time: %.3f ns\n", avgCheckTime);
        System.out.println(ConsoleColor.GREEN + "-".repeat(71) + ConsoleColor.RESET);
    }
}
