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

    private static int recordCount = 0;
    private static int dotCount = 0;

    public boolean load(Map<String, Trace> traceMap, String fileName){
        // Reads CSV file as a stream of RAW bytes
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            System.out.println("Resource not found");
            return false;
        }

        // Start animation thread (this is a separate thread making loading animation)
        // Idea is to separate animation from loading the database (as then would the animation be too fast)
        final boolean[] loadingDone = {false};
        Thread dotThread = new Thread(() -> {
            int dotCount = 0;
            while (!loadingDone[0]) {
                String dots = ".".repeat(dotCount % 4); // "", ".", "..", "..."
                System.out.print("\rFetching data from database" + dots + "   ");
                System.out.flush();
                dotCount++;
                // Increase the dot count after 0.3 seconds
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        });
        dotThread.start();

        // Converts raw bytes into readable text
        try (Reader in = new InputStreamReader(stream, StandardCharsets.ISO_8859_1)){
            // Parses the stream (Readable now) into separate records
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

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

        loadingDone[0] = true; // Stops the animation
        System.out.println("\rDatabase Loaded Successfully");
        System.out.flush();
        return true;
    }
}
