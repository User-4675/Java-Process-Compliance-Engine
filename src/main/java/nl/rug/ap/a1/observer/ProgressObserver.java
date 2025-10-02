package nl.rug.ap.a1.observer;

import nl.rug.ap.a1.Trace;

public interface ProgressObserver {
    void updateProgress(Trace t);
    void reportProgress();
    void reportFinalProgress(long start, long end, long threadTime, long maxMemoryUsed);
}
