package nl.rug.ap.a1;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class Event {
    private final String activity;
    private final String timestamp;
    private final LocalDateTime parsedTime;

    public Event(String activity, String timestamp){
        this.activity = activity;
        this.timestamp = timestamp;
        this.parsedTime = LocalDateTime.parse(timestamp,
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"));
    }
}
