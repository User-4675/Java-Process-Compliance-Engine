package nl.rug.ap.a1;

import nl.rug.ap.a1.observable.ComplianceApp;
import nl.rug.ap.a1.observer.ProgressTracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Maps ID of Traces with actual objects loaded from database
        Map<String, Trace> traceMap = new HashMap<>();
        // Loads data from CSV to Map
        DataLoader loader = new DataLoader();
        // Processes Traces with Multithread Queue
        ComplianceApp multithreadApp = new ComplianceApp();
        // Initialize tracker (Observer) and add the App (observable)
        ProgressTracker tracker = new ProgressTracker();
        multithreadApp.addObserver(tracker);

        // Load Entries from CSV file to Hash Map
        if (!loader.load(traceMap, "BPI_Challenge_2019.csv")){
            return;
        }

        // Run the multithreading evaluation on Traces in configurable thread number
        multithreadApp.startComplianceCheck(traceMap, getConfig());
    }

    private static int getConfig(){
        Scanner scanner = new Scanner(System.in);
        int noOfThreads;

        System.out.println("------ Configuration ------- ");
        while (true){
            System.out.print("Enter number of threads to use: ");
            if (scanner.hasNextInt()){
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
