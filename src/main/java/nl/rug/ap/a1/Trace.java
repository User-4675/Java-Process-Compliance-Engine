package nl.rug.ap.a1;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Trace {
    private final String id;
    private TraceStatus status;
    private final List<Event> events = new ArrayList<>();


    public Trace(String id) {
        this.id = id;
        this.status = TraceStatus.UNKNOWN;
    }

    public void addEvent(Event e) { events.add(e); }

    public void  seeThreadActivity(){
        System.out.println("Thread " + this.id + " Event Log: ");
        for (Event event : events){
            System.out.println(event.getActivity() + " at " + event.getTimestamp());
        }
        System.out.println("--- End Of Record ---");
    }
}
