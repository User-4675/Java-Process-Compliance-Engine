package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.cases.Event;
import nl.rug.ap.a1.cases.Trace;
import nl.rug.ap.a1.cases.TraceStatus;

/**
 * Determines 3-way match (before GR) case status based on following conditions:
 *<ul>
 *     <li> If a ‘Record Invoice Receipt’ occurs before a ‘Record Goods Receipt’, it must
 *         be followed by a ‘Set Payment Block’ event. An early invoice without a block
 *         is a violation.
 *     <li> After the ‘Record Goods Receipt’ occurs, a ‘Remove Payment Block’ event
 *          should happen before the invoice can be cleared.
 *     <li> The ‘Clear Invoice’ event must occur after both the ‘Record Goods Receipt’
 *          and the ‘Remove Payment Block’ events.
 * </ul>
 */
public class ThreeWayBeforeGR implements ComplianceStrategy{

    @Override
    public void check(final Trace trace) {
        trace.setStatus(isCompliant(trace) ? TraceStatus.COMPLIANT : TraceStatus.NONCOMPLIANT);
    }

    @Override
    public boolean isCompliant(final Trace trace){
        return condition1(trace) && condition2(trace) && condition3(trace);
    }

    /**
     * If a ‘Record Invoice Receipt’ occurs before a ‘Record Goods Receipt’, it must
     * be followed by a ‘Set Payment Block’ event. An early invoice without a block
     * is a violation.
     * @param t trace to check
     * @return true if trace complies with above-mentioned rule, else false
     */
    public boolean condition1(final Trace t){
        int countGoods = 0;
        int countInv = 0;
        for (Event e : t.getEvents()) {
            switch (e.getActivity()) {
                case "Record Invoice Receipt":
                    countInv++;
                    break;

                case "Record Goods Receipt":
                case "Record Service Entry Sheet":
                    if (countGoods < countInv) countGoods++;
                    break;

                case "Cancel Goods Receipt":
                    if (countGoods > 0) countGoods--;
                    break;

                case "Cancel Invoice Receipt":
                    countInv--;
                    if (countGoods > 0) countGoods--;
                    break;

                case "Set Payment Block":
                    if (countGoods > 0){
                        countGoods--;
                        countInv--;
                    }
                    break;
            }
        }
        // Are there any goods with preceding invoice without setting the block ?
        return countGoods == 0;
    }

    /** After the ‘Record Goods Receipt’ occurs, a ‘Remove Payment Block’ event
     * should happen before the invoice can be cleared.
     * @param t trace to check
     * @return true if trace complies with above-mentioned rule, else false
     */
    public boolean condition2(final Trace t){
        int goods = 0;
        for (Event e : t.getEvents()){
            switch (e.getActivity()){
                case "Record Goods Receipt":
                case "Record Service Entry Sheet":
                    goods++;
                    break;
                case "Cancel Goods Receipt":
                    goods--;
                    break;
                case "Remove Payment Block":
                    if (goods > 0) goods--;
                    break;
                case "Clear Invoice":
                    if (goods != 0) return false;
            }
            if (goods < 0) return false;
        }
        return goods == 0;
    }

    /**
     * The ‘Clear Invoice’ event must occur after both the ‘Record Goods Receipt’
     * and the ‘Remove Payment Block’ events.
     * @param t trace to check
     * @return true if trace complies with above-mentioned rule, else false
     */
    public boolean condition3(final Trace t){
        boolean cleared = false;
        boolean removed = false;
        for (Event e : t.getEvents()){
            switch (e.getActivity()){
                case "Record Goods Receipt":
                case "Record Service Entry Sheet":
                case "Remove Payment Block":
                    removed = true;
                    break;
                case "Clear Invoice":
                    if (!removed) return false;
                    removed = false;
                    cleared = true;
            }
        }
        return cleared;
    }
}
