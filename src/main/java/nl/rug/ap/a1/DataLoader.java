package nl.rug.ap.a1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DataLoader {

    // FileName Needs to be a resource
    public DataLoader(String fileName, Map<String, Trace> traceMap){
        // Reads CSV file as a stream of RAW bytes
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            System.out.println("Resource not found");
            return;
        }

        // Loads the hash map with converted stream
        load(traceMap, stream);
    }

    private void load(Map<String, Trace> traceMap, InputStream stream){

        // Converts raw bytes into readable text
        try (Reader in = new InputStreamReader(stream, StandardCharsets.ISO_8859_1);){
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

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
