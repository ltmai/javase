package mai.linh.junit;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

class CalendarTest {

    public static void printCalendar(Calendar t) {
        System.out.println(
                String.format("%d-%d-%d %d:%d:%d",
                    t.get(Calendar.DAY_OF_MONTH),
                    t.get(Calendar.MONTH+1),
                    t.get(Calendar.YEAR),
                    t.get(Calendar.HOUR),
                    t.get(Calendar.MINUTE),
                    t.get(Calendar.SECOND)
                    ));
    }

    @Test
    public void throwsExceptionInNonLenientMode() {
        Calendar epoch = new GregorianCalendar();
        epoch.setTimeInMillis(0);

        printCalendar(epoch);
        // 40 years later
        epoch.add(Calendar.YEAR, 40);
        printCalendar(epoch);
    }
}
