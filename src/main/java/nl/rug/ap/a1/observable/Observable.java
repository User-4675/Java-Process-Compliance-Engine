package nl.rug.ap.a1.observable;

import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.observer.ProgressObserver;

/**
 * Represents an observable entity in the observer pattern.
 * <p>
 * Classes implementing this interface can register {@link ProgressObserver}s
 * and notify them about updates or changes in trace processing.
 */
public interface Observable {

    /**
     * Adds a {@link ProgressObserver} to receive updates.
     *
     * @param observer the observer to register
     */
    void addObserver(ProgressObserver observer);

    /**
     * Notifies all registered observers about the processing of a trace.
     *
     * @param trace the {@link Trace} that has been processed
     */
    void notifyObserver(Trace trace);
}
