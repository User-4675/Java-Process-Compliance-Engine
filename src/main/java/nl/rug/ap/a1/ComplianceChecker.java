package nl.rug.ap.a1;

import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Checks the compliance of a {@link Trace} by analyzing its {@link Event} sequence.
 * <p>
 * The compliance check consists of:
 * <ul>
 *     <li>Detecting unknown events that prevent a trace from being classified.</li>
 *     <li>Checking payment cycles to ensure invoices are properly recorded and cleared.</li>
 *     <li>Checking payment blocks to ensure no block violations occur.</li>
 * </ul>
 */
@NoArgsConstructor
public class ComplianceChecker {

    /** List of events considered unknown and preventing classification of a trace. */
    private final List<String> unknownEvents =
            List.of("Change Price", "Change Quantity", "Cancel Subsequent Invoice",
                    "Record Subsequent Invoice", "Vendor creates debit memo");

    /**
     * Performs compliance checking on the given trace.
     * <p>
     * Updates the trace status to {@link TraceStatus#COMPLIANT} or
     * {@link TraceStatus#NONCOMPLIANT} if all events are recognized.
     * If any event is unknown, the status remains {@link TraceStatus#UNKNOWN}.
     *
     * @param trace the trace to check
     */
    public void check(Trace trace) {
        if (containsUnknownEvent(trace)) {
            return; // Leave the trace as UNKNOWN
        }

        trace.sortEvents(); // Sorts events by timestamp
        if (!checkPaymentCycles(trace)) {
            trace.setStatus(TraceStatus.NONCOMPLIANT);
        } else if (!checkPaymentBlocks(trace)) {
            trace.setStatus(TraceStatus.NONCOMPLIANT);
        } else {
            trace.setStatus(TraceStatus.COMPLIANT);
        }
    }

    /**
     * Determines whether the trace contains any unknown events.
     *
     * @param t the trace to inspect
     * @return {@code true} if at least one event is unknown, {@code false} otherwise
     */
    private boolean containsUnknownEvent(Trace t) {
        return t.getEvents().stream().anyMatch(
                e -> unknownEvents.contains(e.getActivity())
        );
    }

    /**
     * Checks the payment cycles within the trace.
     * <p>
     * Ensures that every "Record Invoice Receipt" is eventually followed by a "Clear Invoice",
     * and that no clearing occurs before recording.
     *
     * @param t the trace to check
     * @return {@code true} if payment cycles are valid, {@code false} otherwise
     */
    private boolean checkPaymentCycles(Trace t) {
        int counter = 0;
        for (Event e : t.getEvents()) {
            if (e.getActivity().equals("Record Invoice Receipt")) counter++;
            if (e.getActivity().equals("Clear Invoice")) counter--;
            if (e.getActivity().equals("Cancel Invoice Receipt")) counter--;
            if (counter < 0) return false; // Clear before Record
        }
        return counter == 0; // All records cleared
    }

    /**
     * Checks the payment blocks within the trace.
     * <p>
     * Ensures that "Set Payment Block" and "Remove Payment Block" events are consistent,
     * and that invoices are cleared correctly within open blocks.
     *
     * @param t the trace to check
     * @return {@code true} if payment blocks are valid, {@code false} otherwise
     */
    private boolean checkPaymentBlocks(Trace t) {
        int counter = 0;
        for (Event e : t.getEvents()) {
            if (e.getActivity().equals("Set Payment Block")) counter++;
            if (e.getActivity().equals("Remove Payment Block")) counter--;
            if (e.getActivity().equals("Clear Invoice") && counter != 0) return false;
            if (counter < 0) return false; // Block removed before being set
        }
        return counter == 0; // All blocks properly opened and closed
    }
}
