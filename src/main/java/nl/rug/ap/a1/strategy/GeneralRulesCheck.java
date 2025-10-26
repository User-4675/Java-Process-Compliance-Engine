package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.cases.Event;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;

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
public class GeneralRulesCheck implements ComplianceStrategy {

    /** List of events considered unknown and preventing classification of a trace. */
    private final List<String> unknownEvents =
            List.of("Change Price", "Change Quantity", "Cancel Subsequent Invoice",
                    "Record Subsequent Invoice", "Vendor creates debit memo");

    @Override
    public void check(final Trace trace){
        if (containsUnknownEvent(trace)) trace.setStatus(TraceStatus.UNKNOWN);
        else trace.setStatus(isCompliant(trace) ? TraceStatus.COMPLIANT : TraceStatus.NONCOMPLIANT);
    }

    @Override
    public boolean isCompliant(final Trace trace){
        return (checkPaymentBlocks(trace) && checkPaymentCycles(trace));
    }

    /**
     * Determines whether the trace contains any unknown events.
     *
     * @param t the trace to inspect
     * @return {@code true} if at least one event is unknown, {@code false} otherwise
     */
    private boolean containsUnknownEvent(final Trace t) {
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
    private boolean checkPaymentCycles(final Trace t) {
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
    private boolean checkPaymentBlocks(final Trace t) {
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
