package nl.rug.ap.a1;

/**
 * Represents the status of a {@link Trace}.
 * Each trace can have one of the following statuses:
 *<ul>
 *     <li>{@link #COMPLIANT} – the trace follows all rules or expectations.</li>
 *     <li>{@link #NONCOMPLIANT} – the trace violates some rules or expectations.</li>
 *     <li>{@link #UNKNOWN} – the status of the trace is not determined yet.</li>
 *</ul>
 */
public enum TraceStatus {
    /** The trace is compliant with all rules. */
    COMPLIANT,

    /** The trace is noncompliant with one or more rules. */
    NONCOMPLIANT,

    /** The trace status is unknown. */
    UNKNOWN
}
