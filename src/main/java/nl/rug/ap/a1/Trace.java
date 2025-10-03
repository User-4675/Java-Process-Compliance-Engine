package nl.rug.ap.a1;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a trace consisting of multiple events and a status.
 * <p>
 * Each trace has a unique identifier, a current {@link TraceStatus},
 * and a list of {@link Event} objects associated with it.
 */
@Getter
@Setter
public class Trace {

    /** Unique identifier for the trace. */
    private final String id;

    /** Current status of the trace. */
    private TraceStatus status;

    /** List of events associated with this trace. */
    private final List<Event> events = new ArrayList<>();

    /**
     * Constructs a new {@code Trace} with the given identifier.
     * The initial status is set to {@link TraceStatus#UNKNOWN}.
     *
     * @param id the unique identifier for the trace
     */
    public Trace(String id) {
        this.id = id;
        this.status = TraceStatus.UNKNOWN;
    }

    /**
     * Adds an event to the trace's list of events.
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Sorts the events of this trace in ascending order based on their timestamp.
     */
    public void sortEvents() {
        events.sort(Comparator.comparing(Event::getParsedTime));
    }
}
