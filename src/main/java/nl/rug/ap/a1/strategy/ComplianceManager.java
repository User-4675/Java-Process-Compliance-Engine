package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.Trace;
import nl.rug.ap.a1.TraceType;
import nl.rug.ap.a1.observable.ComplianceApp;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages Strategies for traces
 * */
public class ComplianceManager {
    // Contains all available strategies
    public Map<TraceType, ComplianceStrategy> strategies;

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
        if (strategy != null) strategy.check(trace);
        // Trace is by default UNKNOWN, so we can skip non-matching cases
    }
}
