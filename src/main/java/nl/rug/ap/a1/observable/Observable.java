package nl.rug.ap.a1.observable;

import nl.rug.ap.a1.Trace;
import nl.rug.ap.a1.observer.ProgressObserver;

public interface Observable {

    void addObserver(ProgressObserver observer);
    void notifyObserver(Trace trace);

}
