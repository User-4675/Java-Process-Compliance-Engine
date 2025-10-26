package nl.rug.ap.a1;

import nl.rug.ap.a1.observable.ComplianceApp;
import nl.rug.ap.a1.observer.ProgressTracker;
import nl.rug.ap.a1.reportHandle.ReportGenerator;
import nl.rug.ap.a1.resourceParsing.DataLoader;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.userInterface.Configuration;

import java.io.File;
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
    public static void main(final String[] args) {

        if (args.length < 1) {
            System.out.println("Please provide a CSV file name from event_log folder to process.");
            return;
        }

        String fileName = args[0];
        String filePath = "event_log/" + fileName;
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()){
            System.out.println("The file '" + filePath + "' does not exist or is not a file.");
            return;
        }

        Map<String, Trace> traceMap = new HashMap<>();

        DataLoader loader = new DataLoader();

        ReportGenerator reportGenerator = new ReportGenerator();

        ComplianceApp multithreadComplianceApp = new ComplianceApp();

        Configuration config = new Configuration();

        ProgressTracker tracker = new ProgressTracker();
        multithreadComplianceApp.addObserver(tracker);

        config.getConfiguration();

        System.out.println("\nProcessing file: " + fileName);

        /* Start new process for each file */
        boolean success = loader.load(traceMap, filePath);
        if (!success) {
            System.out.println("Failed to load file: " + fileName);
            return;
        }
        multithreadComplianceApp.startComplianceCheck(
                traceMap, config.getNoOfThreads(), config.isShowLiveProgress()
        );

        /* Create a report */
        success = reportGenerator.generateReport(traceMap, fileName, tracker);
        if (!success) System.out.println("Failed to generate the report for " + fileName);
    }
}
