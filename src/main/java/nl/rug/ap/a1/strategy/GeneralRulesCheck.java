package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.traces.Event;
import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.traces.TraceStatus;

import java.util.List;

public class GeneralRulesCheck implements ComplianceStrategy {

    /** List of events considered unknown and preventing classification of a trace. */
    private final List<String> unknownEvents =
            List.of("Change Price", "Change Quantity", "Cancel Subsequent Invoice",
                    "Record Subsequent Invoice", "Vendor creates debit memo");

    @Override
    public void check(Trace trace){
        if (containsUnknownEvent(trace)) trace.setStatus(TraceStatus.UNKNOWN);
        else trace.setStatus(isCompliant(trace) ? TraceStatus.COMPLIANT : TraceStatus.NONCOMPLIANT);
    }

    @Override
    public boolean isCompliant(Trace trace){
        return (checkPaymentBlocks(trace) && checkPaymentCycles(trace));
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
            switch (e.getActivity()){
                case "Record Invoice Receipt":
                case "Record Service Entry Sheet":
                    counter++;
                    break;
                case "Clear Invoice":
                case "Cancel Invoice Receipt":
                    counter--;
                    break;
            }
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
