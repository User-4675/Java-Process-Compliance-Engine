package nl.rug.ap.a1.strategy;

import nl.rug.ap.a1.traces.Event;
import nl.rug.ap.a1.traces.Trace;
import nl.rug.ap.a1.traces.TraceStatus;

public class ThreeWayBeforeGR implements ComplianceStrategy{

    @Override
    public void check(Trace trace) {
        trace.setStatus(isCompliant(trace) ? TraceStatus.COMPLIANT : TraceStatus.NONCOMPLIANT);
    }

    public boolean isCompliant(Trace trace){
        return condition1(trace) && condition2(trace) && condition3(trace);
    }

    public boolean condition1(Trace t){
        int countGoods = 0, countInv = 0;
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
                    // if invoice is canceled, remove the early flag too
                    countInv--;
                    if (countGoods > 0) countGoods--;
                    break;

                case "Set Payment Block":
                    countGoods--;
                    countInv--;
                    break;

            }
        }
        // Are there any goods with preceding invoice without setting the block ?
        return countGoods == 0;
    }

    public boolean condition2(Trace t){
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

    public boolean condition3(Trace t){
        int count = 0;
        boolean cleared = false;
        for (Event e : t.getEvents()){
            switch (e.getActivity()){
                case "Record Goods Receipt":
                case "Record Service Entry Sheet":
                case "Remove Payment Block":
                    count++;
                    break;
                case "Clear Invoice":
                    cleared = true;
                    count = 0;
            }
            if (cleared && count > 0 ) return false;
        }
        return cleared & count == 0;
    }
}
