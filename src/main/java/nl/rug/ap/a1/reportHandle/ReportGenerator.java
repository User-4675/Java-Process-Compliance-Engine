package nl.rug.ap.a1.reportHandle;

import lombok.NoArgsConstructor;
import nl.rug.ap.a1.cases.Trace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Class responsible for generating CSV reports for compliance traces.
 * <p>
 * Each report contains the following columns: Case ID, Case Type, and Status.
 * The report is stored in a dedicated <code>reports/</code> folder relative
 * to the project root.
 * If the folder does not exist, it will be created automatically.
 */
@NoArgsConstructor
public class ReportGenerator {

    /**
     * Generates a CSV report from a map of {@link Trace} objects.
     * <p>
     * The report file will be created in the <code>reports/</code> directory,
     * and its name is derived from the provided CSV file name by replacing
     * the <code>.csv</code> extension with <code>_report.csv</code>.
     * <p>
     * Each trace in the map corresponds to a row in the CSV, including the
     * trace's ID, type, and compliance status.
     *
     * @param traceMap a map of {@link Trace} objects to be included in the report,
     *              keyed by their case ID
     * @param fileName the original CSV file name; used to derive the report file name
     * @return {@code true} if the report was successfully generated; {@code false} if
     *         the reports folder could not be created or an I/O error occurred
     */
    public boolean generateReport(Map <String, Trace> traceMap, String fileName){
        boolean success = true;
        File reportsFolder = new File("reports");
        if (!reportsFolder.exists()) success = reportsFolder.mkdirs();
        if (!success) return false;

        String reportPath = "reports/" + fileName.replace(".csv", "_report.csv");
        try (FileWriter writer = new FileWriter(reportPath)) {
            // Create a header
            writer.append("\"Case ID\", \"Case Type\", \"Status\"\n");

            for (Trace t : traceMap.values()) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\"\n",
                        t.getId(), t.getType(), t.getStatus()
                ));
            }
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
