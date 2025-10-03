package nl.rug.ap.a1;

import nl.rug.ap.a1.observable.ComplianceApp;
import nl.rug.ap.a1.observer.ProgressTracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Entry point for the compliance checking application.
 * <p>
 * Loads traces from a CSV file, sets up a multithreaded {@link ComplianceApp}
 * with a {@link ProgressTracker} observer, and executes the compliance evaluation.
 * The number of threads can be configured via user input.
 */
public class Main {

    /**
     * Main method to start the compliance checking application.
     * <p>
     * Performs the following steps:
     * <ul>
     *     <li>Creates a map to store traces loaded from CSV</li>
     *     <li>Initializes {@link DataLoader} to load traces</li>
     *     <li>Sets up {@link ComplianceApp} and {@link ProgressTracker}</li>
     *     <li>Loads traces into the map and runs the multithreaded evaluation</li>
     * </ul>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Map<String, Trace> traceMap = new HashMap<>();

        DataLoader loader = new DataLoader();

        ComplianceApp multithreadComplianceApp = new ComplianceApp();

        ProgressTracker tracker = new ProgressTracker();
        multithreadComplianceApp.addObserver(tracker);

        if (!loader.load(traceMap, "BPI_Challenge_2019.csv")) {
            return;
        }

        multithreadComplianceApp.startComplianceCheck(traceMap, getConfig());
    }

    /**
     * Reads the number of threads to use from user input.
     * <p>
     * Continuously prompts the user until a valid integer is entered.
     *
     * @return the number of threads to use for processing traces
     */
    private static int getConfig() {
        Scanner scanner = new Scanner(System.in);
        int noOfThreads;

        System.out.println("------ Configuration ------- ");
        while (true) {
            System.out.print("Enter number of threads to use: ");
            if (scanner.hasNextInt()) {
                noOfThreads = scanner.nextInt();
                break;
            } else {
                System.out.println("Please enter an Integer");
                scanner.next();
            }
        }
        return noOfThreads;
    }
}
