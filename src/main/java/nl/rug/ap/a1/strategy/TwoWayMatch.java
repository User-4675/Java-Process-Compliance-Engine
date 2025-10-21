package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.cases.Event;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;

/**
 * Determines 2way match case status based on following conditions:
 * <ul>
 *      <li>A compliant case for this category must not contain any ‘Record Goods Receipt’ or ‘Record Service Entry Sheet’ events.
 *      <li>The standard process is a simple sequence: ‘Record Invoice Receipt’ → ‘Clear Invoice’.
 * </ul>
 */

public class TwoWayMatch implements ComplianceStrategy{

    @Override
    public void check(Trace trace) {
        trace.setStatus(isCompliant(trace) ? TraceStatus.COMPLIANT : TraceStatus.NONCOMPLIANT);
    }

    @Override
    public boolean isCompliant(Trace trace){
        if (trace.getEvents().stream().anyMatch(e ->
                e.getActivity().equals("Record Goods Receipt") ||
                e.getActivity().equals("Record Service Entry Sheet"))
        ) return false;

        int counter = 0;
        for (Event e : trace.getEvents()){
            if (e.getActivity().equals("Record Invoice Receipt")) counter++;
            if (e.getActivity().equals("Clear Invoice")) counter--;
            if (counter < 0) return false;
        }
        return counter == 0;
    }
}
