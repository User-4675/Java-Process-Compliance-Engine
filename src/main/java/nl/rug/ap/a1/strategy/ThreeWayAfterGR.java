package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.traces.Event;
import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.traces.TraceStatus;

public class ThreeWayAfterGR implements ComplianceStrategy{

    @Override
    public void check(Trace trace){
        trace.setStatus(isCompliant(trace) ? TraceStatus.COMPLIANT : TraceStatus.NONCOMPLIANT);
    }

    @Override
    public boolean isCompliant(Trace trace) {
        int count_goods = 0, count_inv = 0;
        boolean isCleared = false;

        for (Event e : trace.getEvents()) {
            String activity = e.getActivity();

            switch (activity) {
                case "Record Goods Receipt":
                case "Record Service Entry Sheet":
                    count_goods++;
                    break;

                case "Cancel Goods Receipt":
                    if (count_goods > 0) count_goods--;
                    else return false;
                    break;

                case "Record Invoice Receipt":
                    // invoice must come after at least one GR
                    if (count_goods <= count_inv) return false;
                    count_inv++;
                    break;

                case "Cancel Invoice Receipt":
                    // Clearing Non-existent Invoice is covered by general check
                    if (count_inv > 0) count_inv--;
                    break;

                case "Clear Invoice":
                    // clearing requires both GR and IR before it
                    if (count_goods == 0 || count_inv == 0) return false;
                    isCleared = true;
                    // simulate clearing the cycle
                    count_goods--;
                    count_inv--;
                    break;
            }
        }
        return count_goods == 0 && count_inv == 0 && isCleared;
    }
}
