package nl.rug.ap.a1;

import nl.rug.ap.a1.cases.Event;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.cases.TraceType;
import nl.rug.ap.a1.strategy.ComplianceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplianceManagerTest {

    private ComplianceManager manager;

    @BeforeEach
    void setup() {
        manager = new ComplianceManager();
    }

    private Trace traceOf(TraceType type, String... activities) {
        Trace t = new Trace("X1", type.toString());
        int sec = 0;
        for (String a : activities) {
            t.addEvent(new Event(a, String.format("12-10-2025 10:00:%02d.000", sec++)));
        }
        return t;
    }

    @Test
    void routesToThreeWayBeforeGR() {
        Trace t = traceOf(TraceType.ThreeWayBeforeGR,
                "Record Invoice Receipt",
                "Record Goods Receipt",
                "Set Payment Block",
                "Remove Payment Block",
                "Clear Invoice");

        manager.check(t);
        assertNotEquals(TraceStatus.UNKNOWN, t.getStatus());
    }

    @Test
    void routesToThreeWayAfterGR() {
        Trace t = traceOf(TraceType.ThreeWayAfterGR,
                "Record Goods Receipt",
                "Record Invoice Receipt",
                "Clear Invoice");

        manager.check(t);
        assertNotEquals(TraceStatus.UNKNOWN, t.getStatus());
    }

    @Test
    void routesToTwoWayMatch() {
        Trace t = traceOf(TraceType.TwoWayMatch,
                "Record Invoice Receipt",
                "Clear Invoice");

        manager.check(t);
        assertEquals(TraceStatus.COMPLIANT, t.getStatus());
    }

    @Test
    void routesToConsignmentCheck() {
        Trace t = traceOf(TraceType.Consignment,
                "Record Invoice Receipt",
                "Clear Invoice");

        manager.check(t);
        assertEquals(TraceStatus.UNKNOWN, t.getStatus(),
                "Consignment traces should always become UNKNOWN");
    }

    @Test
    void skipsSpecificStrategyIfGeneralRulesFail() {
        // "Change Price" is an unknown event meaning GeneralRulesCheck should set UNKNOWN
        Trace t = traceOf(TraceType.ThreeWayBeforeGR,
                "Change Price",
                "Record Invoice Receipt",
                "Clear Invoice");

        manager.check(t);
        assertEquals(TraceStatus.UNKNOWN, t.getStatus(),
                "GeneralRulesCheck should prevent further checking");
    }

    @Test
    void safeIfTypeHasNoStrategy() {
        Trace t = new Trace("Z1", "POISON");
        manager.check(t);
        // empty trace = counter==0 = COMPLIANT
        assertTrue(t.getStatus() == TraceStatus.COMPLIANT || t.getStatus() == TraceStatus.UNKNOWN);
    }
}
