package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;


/**
 * Marks containment cases as UNKNOWN.
 */
public class ConsignmentCheck implements ComplianceStrategy {

    @Override
    public void check(final Trace trace) {
        trace.setStatus(TraceStatus.UNKNOWN);
    }

    @Override
    public boolean isCompliant(final Trace trace){
        return true;
    }
}
