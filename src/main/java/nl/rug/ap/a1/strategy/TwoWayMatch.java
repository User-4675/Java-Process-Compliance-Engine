package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.traces.Event;
import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.traces.TraceStatus;

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
