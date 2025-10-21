package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.traces.TraceStatus;
import nl.rug.ap.a1.traces.TraceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages Strategies for traces
 * */
public class ComplianceManager {

    // Contains all available strategies
    private final Map<TraceType, ComplianceStrategy> strategies;

    // Contains general rules that every case needs to follow
    private final GeneralRulesCheck general = new GeneralRulesCheck();

    public ComplianceManager(){
        strategies = new HashMap<>();
        initStrategies();
    }

    public void initStrategies(){
        strategies.put(TraceType.ThreeWayBeforeGR, new ThreeWayBeforeGR());
        strategies.put(TraceType.ThreeWayAfterGR, new ThreeWayAfterGR());
        strategies.put(TraceType.TwoWayMatch, new TwoWayMatch());
        strategies.put(TraceType.Consignment, new ConsignmentCheck());
    }

    // Decides for correct check type to be done
    public void check(Trace trace){
        ComplianceStrategy strategy = strategies.get(trace.getType());
        // Check the general criteria first
        general.check(trace);
        // Test for other criteria iff trace passed general check
        if (trace.getStatus() == TraceStatus.COMPLIANT) {
            if (strategy != null) strategy.check(trace);
        }
    }
}
