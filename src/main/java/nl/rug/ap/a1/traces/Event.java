package nl.rug.ap.a1.traces;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event with an activity description and a timestamp.
 * <p>
 * The timestamp is stored as both a {@link String} and a {@link LocalDateTime} object.
 */
@Getter
public class Event {

    /** Description of the activity for this event. */
    private final String activity;

    /** Original timestamp of the event as a string. */
    private final String timestamp;

    /** Parsed timestamp of the event as a {@link LocalDateTime} object. */
    private final LocalDateTime parsedTime;

    /**
     * Constructs a new {@code Event} with the given activity and timestamp.
     *
     * @param activity  the description of the activity
     * @param timestamp the timestamp in the format "dd-MM-yyyy HH:mm:ss.SSS"
     *                  (e.g., "03-10-2025 18:30:15.123")
     * @throws java.time.format.DateTimeParseException if the timestamp is not in the expected format
     */
    public Event(String activity, String timestamp){
        this.activity = activity;
        this.timestamp = timestamp;
        this.parsedTime = LocalDateTime.parse(timestamp,
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"));
    }
}
