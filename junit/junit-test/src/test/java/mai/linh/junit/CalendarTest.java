package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

class CalendarTest {

    private static final int YEAR_TO_ADD = 0;

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

        int epochYear = epoch.get(Calendar.YEAR);
        epoch.add(Calendar.YEAR, YEAR_TO_ADD);
        int newYear = epoch.get(Calendar.YEAR);

        assertEquals(YEAR_TO_ADD, newYear - epochYear);
    }
}
