package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.traces.TraceStatus;

public class ConsignmentCheck implements ComplianceStrategy {

    @Override
    public void check(Trace trace) {
        trace.setStatus(TraceStatus.UNKNOWN);
    }

    @Override
    public boolean isCompliant(Trace trace){
        return true;
    }
}
