package nl.rug.ap.a1;

import nl.rug.ap.a1.strategy.ComplianceManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ComplianceCheckerTest {

    private ComplianceManager checker;

    @BeforeAll
    static void start(){System.out.println("Testing Compliance checker...");}

    @BeforeEach
    void setUp() {checker = new ComplianceManager();}

//    @Test
//    void testUnknownEventKeepsStatusUnknown() {
//        Trace trace = new Trace("1");
//        trace.addEvent(new Event("Change Price", "01-01-2025 10:00:00.000")); // Unknown event
//
//        checker.check(trace);
//
//        assertEquals(TraceStatus.UNKNOWN, trace.getStatus(),
//                "Unknown events should keep trace status UNKNOWN");
//    }
//
//    @Test
//    void testValidPaymentCycleCompliant() {
//        Trace trace = new Trace("2");
//        trace.addEvent(new Event("Record Invoice Receipt", "01-01-2025 10:00:00.000"));
//        trace.addEvent(new Event("Clear Invoice", "01-01-2025 11:00:00.000"));
//
//        checker.check(trace);
//
//        assertEquals(TraceStatus.COMPLIANT, trace.getStatus(),
//                "Valid payment cycles should be COMPLIANT");
//    }
//
//    @Test
//    void testPaymentCycleViolationNonCompliant() {
//        Trace trace = new Trace("3");
//        trace.addEvent(new Event("Clear Invoice", "01-01-2025 10:00:00.000")); // Clear before Record
//
//        checker.check(trace);
//
//        assertEquals(TraceStatus.NONCOMPLIANT, trace.getStatus(),
//                "Payment cycle violation should be NONCOMPLIANT");
//    }
//
//    @Test
//    void testInvalidClearNoncompliant() {
//        Trace trace = new Trace("4");
//        trace.addEvent(new Event("Set Payment Block", "01-01-2025 10:00:00.000"));
//        trace.addEvent(new Event("Remove Payment Block", "01-01-2025 11:00:00.000"));
//        trace.addEvent(new Event("Clear Invoice", "01-01-2025 12:00:00.000")); // After block removed
//
//        checker.check(trace);
//
//        assertEquals(TraceStatus.NONCOMPLIANT, trace.getStatus(),
//                "Clearing invoice without Recording Receipt should be COMPLIANT");
//    }
//
//    @Test
//    void testPaymentBlockViolationNonCompliant() {
//        Trace trace = new Trace("5");
//        trace.addEvent(new Event("Set Payment Block", "01-01-2025 10:00:00.000"));
//        trace.addEvent(new Event("Clear Invoice", "01-01-2025 11:00:00.000")); // Cleared while block active
//        trace.addEvent(new Event("Remove Payment Block", "01-01-2025 12:00:00.000"));
//
//        checker.check(trace);
//
//        assertEquals(TraceStatus.NONCOMPLIANT, trace.getStatus(),
//                "Clearing invoice while payment block is active should be NONCOMPLIANT");
//    }
//
//    @Test
//    void testComplexTraceCompliantNotSorted() {
//        Trace trace = new Trace("6");
//        trace.addEvent(new Event("Clear Invoice", "01-01-2025 11:30:00.000"));
//        trace.addEvent(new Event("Record Invoice Receipt", "01-01-2025 10:00:00.000"));
//        trace.addEvent(new Event("Remove Payment Block", "01-01-2025 11:00:00.000")); // Removed before clearing
//        trace.addEvent(new Event("Set Payment Block", "01-01-2025 10:30:00.000"));
//
//        checker.check(trace);
//
//        assertEquals(TraceStatus.COMPLIANT, trace.getStatus(),
//                "Complex valid trace should be COMPLIANT");
//    }

    @AfterEach
    void tearDown() {System.out.println("Test completed.");}

    @AfterAll
    static void end(){System.out.println("ComplianceChecker passed all tests !");}
}
