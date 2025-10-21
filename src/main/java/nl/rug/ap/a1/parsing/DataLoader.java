package nl.rug.ap.a1.parsing;

import lombok.NoArgsConstructor;
import nl.rug.ap.a1.traces.Event;
import nl.rug.ap.a1.Main;
import nl.rug.ap.a1.traces.Trace;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Loads trace data from a CSV file into a map of {@link Trace} objects.
 * <p>
 * This class reads the CSV file as a stream, parses each record, and converts it into {@link Trace}
 * and {@link Event} objects. It also displays a simple loading animation while reading the file.
 */
@NoArgsConstructor
public class DataLoader {

    /**
     * Loads traces from a CSV file and populates the provided map.
     * <p>
     * Each CSV record is parsed to extract the case ID, activity name, and timestamp.
     * If a trace for a given case ID already exists, the new event is appended; otherwise, a new
     * {@link Trace} is created. The method ensures all events are stored in the map keyed by
     * their case ID.
     *
     * @param traceMap the map to populate with trace objects, keyed by case ID
     * @param fileName the name of the CSV file to load
     * @return {@code true} if the file was successfully loaded; {@code false} otherwise
     */
    public boolean load(Map<String, Trace> traceMap, String fileName) {
        // Reads CSV file as a stream of raw bytes
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            System.out.println("Resource not found");
            return false;
        }

        // Start animation thread (separate from main loading to simulate progress)
        final boolean[] loadingDone = {false};
        Thread dotThread = new Thread(() -> {
            int dotCount = 0;
            while (!loadingDone[0]) {
                String dots = ".".repeat(dotCount % 4);
                System.out.print("\rFetching data from database" + dots + "   ");
                System.out.flush();
                dotCount++;
                try { Thread.sleep(300); }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        dotThread.start();

        // Converts raw bytes into readable text
        try (Reader in = new InputStreamReader(stream, StandardCharsets.ISO_8859_1)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                String caseId = record.get("case concept:name");
                String activity = record.get("event concept:name");
                String timestamp = record.get("event time:timestamp");
                String itemCategory = record.get("case Item Category");
                // System.out.println(itemCategory);

                traceMap.computeIfAbsent(caseId, id -> new Trace(id, itemCategory))
                        .addEvent(new Event(activity, timestamp));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        loadingDone[0] = true; // Stops the animation
        System.out.println("\rDatabase Loaded Successfully");
        System.out.flush();
        return true;
    }
}
