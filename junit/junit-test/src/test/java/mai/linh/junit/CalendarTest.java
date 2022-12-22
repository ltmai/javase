package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

class CalendarTest {

    private static final int YEAR_TO_ADD = 1;

    public static String toString(Calendar t) {
        return String.format("%d-%d-%d %02d:%02d:%02d",
            t.get(Calendar.DAY_OF_MONTH),
            t.get(Calendar.MONTH)+1,
            t.get(Calendar.YEAR),
            t.get(Calendar.HOUR_OF_DAY),
            t.get(Calendar.MINUTE),
            t.get(Calendar.SECOND));
    }

    @Test
    public void test() {
        Locale locale = Locale.GERMANY;
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        Date date = new GregorianCalendar(2000, 11, 12).getTime();

        Calendar timeFL = new GregorianCalendar(TimeZone.getTimeZone("Europe/Helsinki"));
        timeFL.set(2022, 12-1, 30, 00, 00, 00);
        Calendar timeDE = new GregorianCalendar(TimeZone.getTimeZone("Europe/Berlin"));
        timeDE.setTimeInMillis(timeFL.getTimeInMillis());

        System.out.println(df.format(date));
        System.out.println(toString(timeFL));
        System.out.println(toString(timeDE));
    }

    @Test
    public void givenCalendar_addYears_YearAdded() {
        Calendar epoch = new GregorianCalendar();
        epoch.setTimeInMillis(0);

        int epochYear = epoch.get(Calendar.YEAR);
        epoch.add(Calendar.YEAR, YEAR_TO_ADD);
        int newYear = epoch.get(Calendar.YEAR);

        assertEquals(YEAR_TO_ADD, newYear - epochYear);
    }
}
