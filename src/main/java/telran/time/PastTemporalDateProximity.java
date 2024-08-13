package telran.time;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class PastTemporalDateProximity implements TemporalAdjuster{

    private Temporal[] temporals;

    public PastTemporalDateProximity(Temporal[] temporals) {
        this.temporals = temporals;
        Arrays.sort(this.temporals, (t1, t2) -> t1.until(t2, ChronoUnit.DAYS) < 0 ? -1 : 1);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        int index = Arrays.binarySearch(temporals, temporal, (t1, t2) -> t1.until(t2, ChronoUnit.DAYS) < 0 ? -1 : 1);
        if (index < 0) {
            index = -index - 2;
        }
        while (index >= 0 && temporals[index].until(temporal, ChronoUnit.DAYS) > 0) {
            index--;
        }
        if (index >= 0) {
            return temporals[index];
        } else {
            throw new UnsupportedOperationException("No past date found in the array");
        }
    }
}
