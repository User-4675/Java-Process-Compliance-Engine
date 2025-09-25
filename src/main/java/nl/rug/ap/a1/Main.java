package nl.rug.ap.a1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // Maps ID of Traces with actual objects loaded from database
        Map<String, Trace> traceMap = new HashMap<>();
        DataLoader loader = new DataLoader("BPI_Challenge_2019.csv", traceMap);

        // Creates Object (Trace) Queue and loads it with traces
        TraceQueue queue = new TraceQueue();
        for (Trace t : traceMap.values()) {
            queue.enqueue(t);
        }

        queue.dequeue().seeThreadActivity(); // Tests on Trace if loaded correctly
    }
}
