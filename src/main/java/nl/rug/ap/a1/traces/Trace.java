package nl.rug.ap.a1.traces;

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

    /** Type of the trace */
    private TraceType type;

    /** List of events associated with this trace. */
    private final List<Event> events = new ArrayList<>();

    /**
     * Constructs a new {@code Trace} with the given identifier.
     * The initial status is set to {@link TraceStatus#UNKNOWN}.
     *
     * @param id the unique identifier for the trace
     */
    public Trace(String id, String traceType) {
        this.id = id;
        this.status = TraceStatus.UNKNOWN;
        if (traceType.equals("3-way match, invoice after GR")) this.type = TraceType.ThreeWayAfterGR;
        if (traceType.equals("3-way match, invoice before GR")) this.type = TraceType.ThreeWayBeforeGR;
        if (traceType.equals("2-way match")) this.type = TraceType.TwoWayMatch;
        if (traceType.equals("Consignment")) this.type = TraceType.Consignment;
        if (traceType.equals("POISON")) this.type = TraceType.POISON;
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
