package nl.rug.ap.a1;

import nl.rug.ap.a1.cases.Event;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.strategy.GeneralRulesCheck;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GeneralRulesCheckTest {

    private GeneralRulesCheck strategy;

    @BeforeAll
    static void start() {
        System.out.println("Testing GeneralRulesCheck...");
    }

    @BeforeEach
    void setup() {
        strategy = new GeneralRulesCheck();
    }

    private Trace traceOf(String... activities) {
        Trace t = new Trace("T1", "x");
        int sec = 0;
        for (String a : activities) {
            t.addEvent(new Event(a, String.format("10-10-2025 10:00:%02d.000", sec++)));
        }
        return t;
    }

    @Test
    void compliant_basicCycle() {
        Trace t = traceOf("Record Invoice Receipt", "Clear Invoice");
        assertTrue(strategy.isCompliant(t));
    }

    @Test
    void nonCompliant_clearBeforeInvoice() {
        Trace t = traceOf("Clear Invoice", "Record Invoice Receipt");
        assertFalse(strategy.isCompliant(t), "Clear before record should fail");
    }

    @Test
    void nonCompliant_missingClear() {
        Trace t = traceOf("Record Invoice Receipt");
        assertFalse(strategy.isCompliant(t), "Uncleared invoice should fail");
    }

    @Test
    void compliant_withCancelInvoice() {
        Trace t = traceOf("Record Invoice Receipt", "Cancel Invoice Receipt");
        assertTrue(strategy.isCompliant(t), "Cancel should balance invoice");
    }

    @Test
    void compliant_paymentBlockBalanced() {
        Trace t = traceOf(
                "Set Payment Block",
                "Remove Payment Block",
                "Record Invoice Receipt",
                "Clear Invoice"
        );
        assertTrue(strategy.isCompliant(t));
    }

    @Test
    void nonCompliant_clearWhileBlockOpen() {
        Trace t = traceOf(
                "Record Invoice Receipt",
                "Set Payment Block",
                "Clear Invoice"
        );
        assertFalse(strategy.isCompliant(t), "Cannot clear while block open");
    }

    @Test
    void nonCompliant_removeBeforeSet() {
        Trace t = traceOf("Remove Payment Block");
        assertFalse(strategy.isCompliant(t), "Remove before set invalid");
    }

    @Test
    void unknownEvent_marksUnknownStatus() {
        Trace t = traceOf("Change Price", "Record Invoice Receipt", "Clear Invoice");
        strategy.check(t);
        assertEquals(TraceStatus.UNKNOWN, t.getStatus(), "Unknown event should mark trace as UNKNOWN");
    }

    @Test
    void check_setsCorrectStatus() {
        Trace ok = traceOf("Record Invoice Receipt", "Clear Invoice");
        strategy.check(ok);
        assertEquals(TraceStatus.COMPLIANT, ok.getStatus());

        Trace bad = traceOf("Record Invoice Receipt");
        strategy.check(bad);
        assertEquals(TraceStatus.NONCOMPLIANT, bad.getStatus());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test completed.");
    }

    @AfterAll
    static void end() {
        System.out.println("GeneralRulesCheck passed all tests !");
    }
}
