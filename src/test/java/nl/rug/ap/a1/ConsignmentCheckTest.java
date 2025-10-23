package nl.rug.ap.a1;

import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;
import nl.rug.ap.a1.strategy.ConsignmentCheck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsignmentCheckTest {

    @Test
    void check_setsStatusUnknown() {
        Trace trace = new Trace("C1", "Consignment");
        ConsignmentCheck strategy = new ConsignmentCheck();

        strategy.check(trace);

        assertEquals(TraceStatus.UNKNOWN, trace.getStatus(),
                "ConsignmentCheck should always mark status as UNKNOWN");
    }

    @Test
    void isCompliant_alwaysTrue() {
        Trace trace = new Trace("C2", "Consignment");
        ConsignmentCheck strategy = new ConsignmentCheck();

        assertTrue(strategy.isCompliant(trace),
                "isCompliant should always return true");
    }
}
