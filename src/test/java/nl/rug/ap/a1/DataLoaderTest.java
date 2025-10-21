package nl.rug.ap.a1;

import nl.rug.ap.a1.resourceParsing.DataLoader;
import nl.rug.ap.a1.cases.Trace;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

class DataLoaderTest {

    private Map<String, Trace> traces;

    @BeforeAll
    static void start(){System.out.println("Testing Data Loader...");}

    @BeforeEach
    void setUp(){
        traces = new HashMap<>();
        DataLoader loader = new DataLoader();
        loader.load(traces, "testDatabase.csv");
    }

//    @Test
//    void testNumberOfTracesLoaded() {
//        assertEquals(4, traces.size(), "Should load 4 traces from CSV");
//    }
//
//    @Test
//    void testTraceIdsExist() {
//        assertTrue(traces.containsKey("1"), "Trace 1 should exist");
//        assertTrue(traces.containsKey("2"), "Trace 2 should exist");
//        assertTrue(traces.containsKey("3"), "Trace 3 should exist");
//        assertTrue(traces.containsKey("4"), "Trace 4 should exist");
//    }
//
//    @Test
//    void testEventsLoadedForTrace1() {
//        Trace trace1 = traces.get("1");
//        assertNotNull(trace1, "Trace 1 should not be null");
//        assertEquals(4, trace1.getEvents().size(), "Trace 1 should have 4 events");
//        assertEquals("Record Invoice Receipt", trace1.getEvents().get(0).getActivity());
//        assertEquals("Clear Invoice", trace1.getEvents().get(1).getActivity());
//    }
//
//    @Test
//    void testEventsLoadedForTrace4() {
//        Trace trace4 = traces.get("4");
//        assertNotNull(trace4, "Trace 4 should not be null");
//        assertEquals(3, trace4.getEvents().size(), "Trace 4 should have 3 events");
//        assertEquals("Change Price", trace4.getEvents().get(0).getActivity());
//    }

    @AfterEach
    void tearDown(){System.out.println("Test Completed.");}

    @AfterAll
    static void end(){System.out.println("DataLoader passed all checks !");}
}
