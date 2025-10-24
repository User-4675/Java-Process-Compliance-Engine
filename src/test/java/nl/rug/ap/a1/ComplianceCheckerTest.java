package nl.rug.ap.a1;

import nl.rug.ap.a1.cases.Event;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.strategy.ComplianceManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ComplianceCheckerTest {

    private ComplianceManager checker;

    @BeforeAll
    static void start() {
        System.out.println("Testing ComplianceChecker...");
    }

    @BeforeEach
    void setUp() {
        checker = new ComplianceManager();
    }

    @Test
    void testUnknownEventKeepsStatusUnknown() {
        Trace trace = new Trace("1", "2-way match");
        trace.addEvent(new Event("Change Price", "01-01-2025 10:00:00.000"));

        checker.check(trace);

        assertEquals(TraceStatus.UNKNOWN, trace.getStatus());
    }

    @Test
    void testValidPaymentCycleCompliant() {
        Trace trace = new Trace("2", "2-way match");
        trace.addEvent(new Event("Record Invoice Receipt", "01-01-2025 10:00:00.000"));
        trace.addEvent(new Event("Clear Invoice", "01-01-2025 11:00:00.000"));

        checker.check(trace);

        assertEquals(TraceStatus.COMPLIANT, trace.getStatus());
    }

    @Test
    void testPaymentCycleViolationNonCompliant() {
        Trace trace = new Trace("3", "2-way match");
        trace.addEvent(new Event("Clear Invoice", "01-01-2025 10:00:00.000"));

        checker.check(trace);

        assertEquals(TraceStatus.NONCOMPLIANT, trace.getStatus());
    }

    @Test
    void testInvalidClearNoncompliant() {
        Trace trace = new Trace("4", "2-way match");
        trace.addEvent(new Event("Set Payment Block", "01-01-2025 10:00:00.000"));
        trace.addEvent(new Event("Remove Payment Block", "01-01-2025 11:00:00.000"));
        trace.addEvent(new Event("Clear Invoice", "01-01-2025 12:00:00.000"));

        checker.check(trace);

        assertEquals(TraceStatus.NONCOMPLIANT, trace.getStatus());
    }

    @Test
    void testPaymentBlockViolationNonCompliant() {
        Trace trace = new Trace("5", "2-way match");
        trace.addEvent(new Event("Record Invoice Receipt", "01-01-2025 09:55:00.000"));
        trace.addEvent(new Event("Set Payment Block", "01-01-2025 10:00:00.000"));
        trace.addEvent(new Event("Clear Invoice", "01-01-2025 11:00:00.000"));
        trace.addEvent(new Event("Remove Payment Block", "01-01-2025 12:00:00.000"));

        checker.check(trace);

        assertEquals(TraceStatus.NONCOMPLIANT, trace.getStatus());
    }

    @Test
    void testComplexTraceCompliantAfterSorting() {
        Trace trace = new Trace("6", "2-way match");
        trace.addEvent(new Event("Clear Invoice", "01-01-2025 11:30:00.000"));
        trace.addEvent(new Event("Record Invoice Receipt", "01-01-2025 10:00:00.000"));
        trace.addEvent(new Event("Remove Payment Block", "01-01-2025 11:00:00.000"));
        trace.addEvent(new Event("Set Payment Block", "01-01-2025 10:30:00.000"));

        trace.sortEvents();

        checker.check(trace);

        assertEquals(TraceStatus.COMPLIANT, trace.getStatus());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test completed.");
    }

    @AfterAll
    static void end() {
        System.out.println("ComplianceChecker passed all tests !");
    }
}
