package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.cases.Event;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;

/**
 * Determines 3-way match (after GR) case status based on following conditions:
 *<ul>
 *     <li> A compliant case for this category must not contain any ‘Record Goods Receipt’
 *          or ‘Record Service Entry Sheet’ events.
 *     <li> The standard process is a simple sequence: ‘Record Invoice Receipt’ → ‘Clear
 *          Invoice’.
 * </ul>
 */
public class ThreeWayAfterGR implements ComplianceStrategy{

    @Override
    public void check(final Trace trace){
        trace.setStatus(isCompliant(trace) ? TraceStatus.COMPLIANT : TraceStatus.NONCOMPLIANT);
    }

    @Override
    public boolean isCompliant(final Trace trace) {
        int countGoods = 0;
        int countInv = 0;
        boolean isCleared = false;

        for (Event e : trace.getEvents()) {
            String activity = e.getActivity();

            switch (activity) {
                case "Record Goods Receipt":
                case "Record Service Entry Sheet":
                    countGoods++;
                    break;

                case "Cancel Goods Receipt":
                    if (countGoods > 0) countGoods--;
                    else return false;
                    break;

                case "Record Invoice Receipt":
                    // invoice must come after at least one GR
                    if (countGoods <= countInv) return false;
                    countInv++;
                    break;

                case "Cancel Invoice Receipt":
                    // Clearing Non-existent Invoice is covered by general check
                    countInv--;
                    break;

                case "Clear Invoice":
                    // clearing requires both GR and IR before it
                    if (countGoods == 0 || countInv == 0) return false;
                    isCleared = true;
                    // simulate clearing the cycle
                    countGoods--;
                    countInv--;
                    break;
            }
        }
        return countGoods == 0 && countInv == 0 && isCleared;
    }
}
