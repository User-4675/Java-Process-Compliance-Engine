package nl.rug.ap.a1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Configuration}.
 * These tests simulate user input through a ByteArrayInputStream.
 */
public class ConfigurationTest {

    @BeforeAll
    static void start(){System.out.println("Testing Configuration...");}

    @Test
    void testValidConfigurationInput() {
        String input = "4\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Configuration config = new Configuration();
        config.getConfiguration();

        assertEquals(4, config.getNoOfThreads());
        assertTrue(config.isShowLiveProgress());
    }

    @Test
    void testInvalidThenValidThreadInput() {
        String input = "abc\n3\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Configuration config = new Configuration();
        config.getConfiguration();

        assertEquals(3, config.getNoOfThreads());
        assertFalse(config.isShowLiveProgress());
    }

    @Test
    void testInvalidThenValidProgressInput() {
        String input = "2\nmaybe\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Configuration config = new Configuration();
        config.getConfiguration();

        assertEquals(2, config.getNoOfThreads());
        assertTrue(config.isShowLiveProgress());
    }

    @Test
    void testLowercaseUppercaseProgressInput() {
        String input = "5\nN\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Configuration config = new Configuration();
        config.getConfiguration();

        assertEquals(5, config.getNoOfThreads());
        assertFalse(config.isShowLiveProgress());
    }

    @AfterEach
    void teardown(){System.out.println("\nTest Completed.");}

    @AfterAll
    static void end(){System.out.println("Configuration test completed !");}
}
