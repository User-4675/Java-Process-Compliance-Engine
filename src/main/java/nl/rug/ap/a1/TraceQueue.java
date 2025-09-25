package nl.rug.ap.a1;

import java.util.LinkedList;
import java.util.Queue;

// This class implements the queue that will store the Threads
public class TraceQueue {
    private Queue<Trace> queue;

    public TraceQueue(){
        this.queue = new LinkedList<>();
    }

    public void enqueue(Trace trace) {
        queue.add(trace);
    }

    public Trace dequeue() {
        return queue.poll();
    }

    public Trace peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Optional: performance metrics
    public int size() {
        return queue.size();
    }
}
