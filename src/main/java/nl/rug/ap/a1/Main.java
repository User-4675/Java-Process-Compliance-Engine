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

        // Reads CSV file as a stream of RAW bytes
        InputStream stream = Main.class.getClassLoader().getResourceAsStream("BPI_Challenge_2019.csv");
        if (stream == null) {
            System.out.println("Resource not found");
            return;
        }
        // Converts raw bytes into readable text
        Reader in = new InputStreamReader(stream, StandardCharsets.ISO_8859_1);

        // CSV parsing and reading can throw IOException, so we need to handle it
        try {
            // Parses the stream (Readable now) into separate records
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader() // As seen in csv, first record gives header instead of data
                    .parse(in);

            // Iterate over each record
            for (CSVRecord record : records) {
                // Get its ID, activity and time
                String caseId = record.get("case concept:name");
                String activity = record.get("event concept:name");
                String timestamp = record.get("event time:timestamp");
                System.out.println(caseId + " -- " + activity + " -- " + timestamp);

                // Stores the case into map --> <"CaseID" or we refer ot it as Thread, Trace object>
                // Finds if case already exists, otherwise creates it
                // Then appends the event to the case number
                traceMap.computeIfAbsent(caseId, Trace::new).addEvent(new Event(activity, timestamp));
            }

            // Creates Object (Trace) Queue and loads it with traces
            TraceQueue queue = new TraceQueue();
            for (Trace t : traceMap.values()) {
                queue.enqueue(t);
            }

            queue.dequeue().seeThreadActivity();

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
