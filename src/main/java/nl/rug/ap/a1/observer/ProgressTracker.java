package nl.rug.ap.a1.observer;

import java.lang.System;
import lombok.NoArgsConstructor;
import nl.rug.ap.a1.Trace;

@NoArgsConstructor
public class ProgressTracker implements ProgressObserver {

    // Colors for Statuses
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[94m";
    public static final String YELLOW = "\u001B[33m";

    // These variables keep track of progress done by App
    private int processed = 0;
    private int compliant = 0;
    private int nonCompliant = 0;
    private int unknown = 0;

    // This function updates tracker after every processed trace
    @Override
    public synchronized void updateProgress(Trace t){
        processed++;
        switch (t.getStatus()) {
            case UNKNOWN:
                unknown++;
                break;
            case COMPLIANT:
                compliant++;
                break;
            case NONCOMPLIANT:
                nonCompliant++;
                break;
        }
    }

    @Override
    public void reportProgress(){
        System.out.printf(
                "\rProcessed: %d Compliant: %d Non-compliant: %d Unknown: %d    ",
                processed, compliant, nonCompliant, unknown);
        System.out.flush(); // Flushes the input until \r (makes progress seem alive)
    }

    @Override
    public void reportFinalProgress(){
        System.out.print(GREEN + "\n[ALL CASES PROCESSED]" + RESET + "\n---- Report ---- \n");
        System.out.println("Processed: " + processed
                + GREEN + " Compliant: " + RESET + compliant
                + RED + " Non-Compliant: " + RESET + nonCompliant
                + YELLOW + " Unknown: " + RESET + unknown);
    }
}
