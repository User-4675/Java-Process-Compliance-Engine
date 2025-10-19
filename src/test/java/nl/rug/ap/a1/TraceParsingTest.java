package nl.rug.ap.a1;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TraceParsingTest {

    private Trace trace;
    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");

    @BeforeAll
    static void start(){System.out.println("Starting trace parsing test...");}

    @BeforeEach
//    void setup(){
//        trace = new Trace("Test");
//        trace.addEvent(new Event("Set Payment Block", "01-10-2025 08:17:00.000"));
//        trace.addEvent(new Event("Record Invoice Receipt", "01-10-2025 08:15:00.000"));
//        trace.addEvent(new Event("Remove Payment Block", "01-10-2025 08:18:00.000"));
//        trace.addEvent(new Event("Clear Invoice", "01-10-2025 08:25:00.000"));
//        trace.addEvent(new Event("Clear Invoice", "01-10-2025 08:20:00.000"));
//        trace.addEvent(new Event("Record Invoice Receipt", "01-10-2025 08:10:00.000"));
//    }
//
//    @Test
//    void testTraceContainsEvents() {
//        assertNotNull(trace.getEvents(), "Events list is null");
//        assertEquals(6, trace.getEvents().size(), "Trace should contain 6 events");
//    }
//
//    @Test
//    void testTimestampsCanBeParsed() {
//        for (Event e : trace.getEvents()) {
//            assertDoesNotThrow(() ->
//                            LocalDateTime.parse(e.getTimestamp().trim(), formatter),
//                    "Failed to parse timestamp: " + e.getTimestamp());
//        }
//    }
//
//    @Test
//    void testEventsAreSortedChronologically() {
//        List<Event> events = trace.getEvents();
//        trace.sortEvents();
//        for (int i = 1; i < events.size(); i++) {
//            LocalDateTime prev = LocalDateTime.parse(events.get(i - 1).getTimestamp().trim(), formatter);
//            LocalDateTime curr = LocalDateTime.parse(events.get(i).getTimestamp().trim(), formatter);
//            assertFalse(curr.isBefore(prev),
//                    "Events are not sorted: " + events.get(i - 1).getActivity()
//                            + " before " + events.get(i).getActivity());
//        }
//    }
//
//    @Test
//    void testEventNamesMatchExpectedPattern() {
//        List<String> validEvents = List.of(
//                "Record Invoice Receipt",
//                "Clear Invoice",
//                "Set Payment Block",
//                "Remove Payment Block",
//                "Cancel Invoice Receipt"
//        );
//
//        for (Event e : trace.getEvents()) {
//            assertTrue(validEvents.contains(e.getActivity()),
//                    "Unexpected event name: " + e.getActivity());
//        }
//    }

    @AfterEach
    void teardown(){System.out.println("Test Completed.");}

    @AfterAll
    static void end(){System.out.println("Trace parsing test completed !");}
}
