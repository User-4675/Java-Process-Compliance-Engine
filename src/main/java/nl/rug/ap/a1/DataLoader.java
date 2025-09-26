package nl.rug.ap.a1;

import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@NoArgsConstructor
public class DataLoader {

    public boolean load(Map<String, Trace> traceMap, String fileName){
        // Reads CSV file as a stream of RAW bytes
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            System.out.println("Resource not found");
            return false;
        }

        // Converts raw bytes into readable text
        try (Reader in = new InputStreamReader(stream, StandardCharsets.ISO_8859_1)){
            // Parses the stream (Readable now) into separate records
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

            System.out.println("Fetching data from database...");

            // Iterate over each record
            for (CSVRecord record : records) {
                // Get its ID, activity and time
                String caseId = record.get("case concept:name");
                String activity = record.get("event concept:name");
                String timestamp = record.get("event time:timestamp");

                // Stores the Trace into map --> <"CaseID", Trace object>
                // Finds if case already exists, otherwise creates its
                // Then appends the event to the case number
                traceMap.computeIfAbsent(caseId, Trace::new).addEvent(new Event(activity, timestamp));
            }

        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        System.out.println("Database Loaded Successfully");
        return true;
    }
}
