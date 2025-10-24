package nl.rug.ap.a1;

import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.resourceParsing.DataLoader;
import nl.rug.ap.a1.strategy.ThreeWayAfterGR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThreeWayAfterGRTest {

    private ThreeWayAfterGR strategy;
    private Map<String, Trace> traces;

    @BeforeEach
    void setUp() {
        strategy = new ThreeWayAfterGR();
        traces = new HashMap<>();

        DataLoader loader = new DataLoader();
        boolean ok = loader.load(traces, "src/test/resources/testDatabase-ThreeWayAfter.csv");
        assertTrue(ok, "Failed to load");
    }

    @Test
    void evaluateCsvCases() {
        for (Trace t : traces.values()) {
            strategy.check(t);
        }

        assertEquals(TraceStatus.COMPLIANT,    traces.get("1").getStatus(), "1 should be compliant");
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("2").getStatus(), "2 should be noncompliant");
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("3").getStatus(), "3 should be noncompliant");
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("4").getStatus(), "4 should be noncompliant");
        assertEquals(TraceStatus.COMPLIANT,    traces.get("5").getStatus(), "5 should be compliant");
        assertEquals(TraceStatus.COMPLIANT,    traces.get("6").getStatus(), "6 should be compliant");
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("7").getStatus(), "7 should be noncompliant");
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("8").getStatus(), "8 should be noncompliant");
    }
}
