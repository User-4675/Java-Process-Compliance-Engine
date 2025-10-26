package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.cases.TraceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages correct strategy selection based on case criteria.
 */
public class ComplianceManager {

    /** Contains all available strategies. */
    private final Map<TraceType, ComplianceStrategy> strategies;

    /** Contains general rules that every case needs to follow. */
    private final GeneralRulesCheck general = new GeneralRulesCheck();

    /**
     * Constructs Manager.
     */
    public ComplianceManager(){
        strategies = new HashMap<>();
        initStrategies();
    }

    /**
     * Initializes strategies.
     */
    public void initStrategies(){
        strategies.put(TraceType.ThreeWayBeforeGR, new ThreeWayBeforeGR());
        strategies.put(TraceType.ThreeWayAfterGR, new ThreeWayAfterGR());
        strategies.put(TraceType.TwoWayMatch, new TwoWayMatch());
        strategies.put(TraceType.Consignment, new ConsignmentCheck());
    }

    /**
     * Decides for correct check type to be done.
     * @param trace a trace to evaluate
     * */
    public void check(final Trace trace){
        ComplianceStrategy strategy = strategies.get(trace.getType());
        // Ensure that events are sorted
        trace.sortEvents();
        // Check the general criteria first
        general.check(trace);
        // Test for other criteria iff trace passed general check
        if (trace.getStatus() == TraceStatus.COMPLIANT) {
            if (strategy != null) strategy.check(trace);
        }
    }
}
