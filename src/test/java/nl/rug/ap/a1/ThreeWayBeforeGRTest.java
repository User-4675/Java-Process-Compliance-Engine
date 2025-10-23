package nl.rug.ap.a1;

import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.resourceParsing.DataLoader;
import nl.rug.ap.a1.strategy.ThreeWayBeforeGR;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThreeWayBeforeGRTest {

    private ThreeWayBeforeGR strategy;
    private Map<String, Trace> traces;

    @BeforeEach
    void setUp() {
        strategy = new ThreeWayBeforeGR();
        traces = new HashMap<>();

        DataLoader loader = new DataLoader();
        boolean ok = loader.load(traces, "testDatabase-ThreeWayBefore.csv");
        assertTrue(ok, "Failed to load");
    }

    @Test
    void evaluateCsvCases() {
        for (Trace t : traces.values()) {
            strategy.check(t);
        }

        assertEquals(TraceStatus.COMPLIANT,    traces.get("1").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("2").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("3").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("4").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("5").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("6").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("7").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("8").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("9").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("10").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("11").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("12").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("13").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("14").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("15").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("16").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("17").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("18").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("19").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("20").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("21").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("22").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("23").getStatus());
        assertEquals(TraceStatus.NONCOMPLIANT, traces.get("24").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("25").getStatus());
        assertEquals(TraceStatus.COMPLIANT,    traces.get("26").getStatus());
    }
}
