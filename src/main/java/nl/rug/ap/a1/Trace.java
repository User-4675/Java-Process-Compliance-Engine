package nl.rug.ap.a1;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class Trace {
    private final String id;
    private TraceStatus status;
    private final List<Event> events = new ArrayList<>();

    public Trace(String id) {
        this.id = id;
        this.status = TraceStatus.UNKNOWN;
    }

    public void addEvent(Event e) { events.add(e); }

    public void sortEvents(){
        events.sort(Comparator.comparing(Event::getParsedTime));
    }
}
