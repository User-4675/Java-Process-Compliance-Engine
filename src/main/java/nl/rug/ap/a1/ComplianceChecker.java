package nl.rug.ap.a1;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ComplianceChecker {

    private final List<String> unknownEvents =
            List.of("Change Price", "Change Quantity", "Cancel Subsequent Invoice",
                    "Record Subsequent Invoice", "Vendor creates debit memo");

    public void check(Trace trace){
        if (containsUnknownEvent(trace)){
            // Leave the trace as UNKNOWN
            return;
        }

        trace.sortEvents(); // Sorts Events by timeStamp
        if (!checkPaymentCycles(trace)){
            trace.setStatus(TraceStatus.NONCOMPLIANT);
        } else if (!checkPaymentBlocks(trace)){
            trace.setStatus(TraceStatus.NONCOMPLIANT);
        } else {
            trace.setStatus(TraceStatus.COMPLIANT);
        }
    }

    // Gets all the events for trace and checks if any of them
    // are UNKNOWN events from unknownEvents
    private boolean containsUnknownEvent(Trace t){
        return t.getEvents().stream().anyMatch(
                e -> unknownEvents.contains(e.getActivity())
        );
    }

    // Checks weather every "Record Invoice Receipt" is followed
    // by "Clear Invoice"
    private boolean checkPaymentCycles(Trace t){
        int counter = 0;
        for (Event e : t.getEvents()){
            if (e.getActivity().equals("Record Invoice Receipt")) counter++;
            if (e.getActivity().equals("Clear Invoice")) counter--;
            if (e.getActivity().equals("Cancel Invoice Receipt")) counter--;
            // If at any point, there is Clear before Record
            if (counter < 0) return false;
        }
        // Check for exact match if Records and Clears
        return counter == 0;
    }

    // Checks Payment Blocks (PB)
    private boolean checkPaymentBlocks(Trace t){
        int counter = 0;
        for (Event e: t.getEvents()){
            if (e.getActivity().equals("Set Payment Block")) counter++;
            if (e.getActivity().equals("Remove Payment Block")) counter--;
            // Check if invoice is cleared before closing the PB
            if (e.getActivity().equals("Clear Invoice") && counter != 0) return false;
            // Check if at any point the block is removed before being set
            if (counter < 0) return false;
        }
        // Check for exact match of Opened and Closed PBs
        return counter == 0;
    }

}
