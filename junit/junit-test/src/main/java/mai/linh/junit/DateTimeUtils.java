package mai.linh.junit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class DateTimeUtils {
    
    public static class Interval 
    {
        public Temporal from;
        public Temporal to;
        public static Interval of(Temporal from, Temporal to) {
            Interval result = new Interval();
            result.from = from;
            result.to = to;
            return result;
        }
        @Override
        public String toString() {
            return String.format("From %s to %s", from.toString(), to.toString());
        }
    }

    public static enum IntervalType {
        DAY(IntervalType::breakDownDay),
        WEEK(IntervalType::breakDownWeek),
        MONTH(IntervalType::breakDownMonth);

        private BiFunction<LocalDate, LocalDate, List<Interval>> breakDownMethod;

        IntervalType(BiFunction<LocalDate, LocalDate, List<Interval>> breakDownMethod) {
            this.breakDownMethod = breakDownMethod;
        }

        public List<Interval> breakDown(LocalDate fromDate, LocalDate toDate)
        {
            return breakDownMethod.apply(fromDate, toDate);
        }

        static private List<Interval> breakDownDay(LocalDate fromDate, LocalDate toDate)
        {
            return new ArrayList<>();
        }

        static private List<Interval> breakDownWeek(LocalDate fromDate, LocalDate toDate)
        {
            List<Interval> intervals = new ArrayList<>();

            if (!toDate.isBefore(fromDate)) {
                LocalDate intervalFrom = fromDate;
                LocalDate intervalTo = fromDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

                while (intervalTo.isBefore(toDate)) {
                    intervals.add(Interval.of(intervalFrom, intervalTo));
                    intervalFrom = intervalTo.plusDays(1L);
                    intervalTo = intervalFrom.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                }
                if (intervalTo.isAfter(toDate))
                {
                    intervalTo = toDate;
                }
                intervals.add(Interval.of(intervalFrom, intervalTo));
            }

            return intervals; 
        }

        static private List<Interval> breakDownMonth(LocalDate fromDate, LocalDate toDate)
        {
            List<Interval> intervals = new ArrayList<>();

            if (!toDate.isBefore(fromDate)) {
                LocalDate intervalFrom = fromDate;
                LocalDate intervalTo = fromDate.with(TemporalAdjusters.lastDayOfMonth());

                while (intervalTo.isBefore(toDate)) {
                    intervals.add(Interval.of(intervalFrom, intervalTo));
                    intervalFrom = intervalTo.plusDays(1L);
                    intervalTo = intervalFrom.with(TemporalAdjusters.lastDayOfMonth());
                }
                if (intervalTo.isAfter(toDate))
                {
                    intervalTo = toDate;
                }
                intervals.add(Interval.of(intervalFrom, intervalTo));
            }

            return intervals;
        }
    }
}
