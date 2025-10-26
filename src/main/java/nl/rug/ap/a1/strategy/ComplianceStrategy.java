package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.cases.Trace;

/**
 * Represents a compliance strategy for individual case types.
 */
public interface ComplianceStrategy {

    /**
     * Marks the trace with correct {@link nl.rug.ap.a1.cases.TraceStatus}.
     * @param trace trace to mark
     */
    void check(Trace trace);

    /**
     * Checks for compliance of the trace.
     * @param trace trace to check
     * @return true of compliant, else false
     */
    boolean isCompliant(Trace trace);
}
