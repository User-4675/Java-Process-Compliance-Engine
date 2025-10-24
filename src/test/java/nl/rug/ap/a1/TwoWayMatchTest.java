package nl.rug.ap.a1;

import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.resourceParsing.DataLoader;
import nl.rug.ap.a1.strategy.TwoWayMatch;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TwoWayMatchTest {

    private TwoWayMatch strategy;
    private Map<String, Trace> traces;

    @BeforeAll
    static void start() {
        System.out.println("Testing TwoWayMatch...");
    }

    @BeforeEach
    void setUp() {
        strategy = new TwoWayMatch();
        traces = new HashMap<>();

        DataLoader loader = new DataLoader();
        boolean ok = loader.load(traces, "src/test/resources/testDatabase-TwoWay.csv");
        assertTrue(ok, "Failed to load test CSV");
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
        assertEquals(TraceStatus.COMPLIANT,    traces.get("5").getStatus());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test completed.");
    }

    @AfterAll
    static void end() {
        System.out.println("TwoWayMatch passed all tests !");
    }
}
