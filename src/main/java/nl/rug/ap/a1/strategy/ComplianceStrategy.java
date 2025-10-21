package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.traces.Trace;

public interface ComplianceStrategy {
    void check(Trace trace);
    boolean isCompliant(Trace trace);
}
