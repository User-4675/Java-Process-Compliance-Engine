package nl.rug.ap.a1;

import nl.rug.ap.a1.observable.ComplianceApp;
import nl.rug.ap.a1.observer.ProgressTracker;
import nl.rug.ap.a1.parsing.DataLoader;
import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.userInterface.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Entry point for the compliance checking application.
 * <p>
 * Loads traces from a CSV file, sets up a multithreaded {@link ComplianceApp}
 * with a {@link ProgressTracker} observer, and executes the compliance evaluation.
 * The number of threads and live progress can be configured via user input.
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

        Configuration config = new Configuration();

        ProgressTracker tracker = new ProgressTracker();
        multithreadComplianceApp.addObserver(tracker);

        if (!loader.load(traceMap, "BPI_Challenge_2019.csv")) {
            return;
        }

        config.getConfiguration();

        multithreadComplianceApp.startComplianceCheck(traceMap, config.getNoOfThreads(), config.isShowLiveProgress());
    }
}
