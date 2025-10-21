package nl.rug.ap.a1.cases;

/**
 * Enumeration of trace types used for compliance checking.
 * Each type represents a different invoicing and goods receipt workflow.
 */
public enum TraceType {

    /**
     * Standard 3-way match: invoice occurs after goods receipt.
     * This is the most controlled flow.
     */
    ThreeWayAfterGR,

    /**
     * Flexible 3-way match: invoice may occur before goods receipt.
     * Requires a Set Payment Block before goods can be cleared.
     */
    ThreeWayBeforeGR,

    /**
     * 2-way match: no goods receipt required.
     * Standard sequence is Record Invoice Receipt → Clear Invoice.
     */
    TwoWayMatch,

    /**
     * Consignment items: invoicing handled externally.
     * Cannot be checked for compliance and should be flagged as UNKNOWN.
     */
    Consignment,

    /**
     * Special marker used internally, e.g., for signaling the end of a queue or stream.
     */
    POISON
}
