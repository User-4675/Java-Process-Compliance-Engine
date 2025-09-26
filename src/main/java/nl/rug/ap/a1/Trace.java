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

    // Colors for Statuses
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[94m";
    public static final String YELLOW = "\u001B[33m";

    public Trace(String id) {
        this.id = id;
        this.status = TraceStatus.UNKNOWN;
    }

    public void addEvent(Event e) { events.add(e); }

    public void sortEvents(){
        events.sort(Comparator.comparing(Event::getParsedTime));
    }

    public void seeTrace(){
        System.out.println("Trace " + BLUE +  this.id + RESET + " Event Log: ");
        for (Event event : events){
            System.out.println("    -> " + event.getActivity() + " at " + event.getTimestamp());
        }

        // Sets color for status
        System.out.print("Status: ");
        if (this.status == TraceStatus.COMPLIANT) System.out.print(GREEN);
        else if (this.status == TraceStatus.NONCOMPLIANT) System.out.print(RED);
        else System.out.print(YELLOW);

        // Print status and reset color
        System.out.println(this.status + RESET);
        System.out.println("--- End Of Record ---");
    }
}
