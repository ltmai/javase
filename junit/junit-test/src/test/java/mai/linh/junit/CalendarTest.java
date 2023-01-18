package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
    public void dateFormat_withPatterns_giveExpectedResult()
    {
        Locale locale = Locale.GERMANY;
        //Locale locale = Locale.CHINA;

        Date date = new GregorianCalendar(2022, 12 - 1, 24).getTime();

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        DateFormat monthFormat = new SimpleDateFormat("MMM yyyy", locale);
        DateFormat weekFormat = new SimpleDateFormat("'KW'w yyyy", locale);

        //System.out.println("Date : " + dateFormat.format(date));
        //System.out.println("Week : " + weekFormat.format(date));
        //System.out.println("Month: " + monthFormat.format(date));

        assertEquals("24.12.2022", dateFormat.format(date));
        assertEquals("Dez. 2022", monthFormat.format(date));
        assertEquals("KW51 2022", weekFormat.format(date));
    }

    @Test
    public void givenDateInFinland_convertToGermanTime_getCorrectResults() {
        Calendar timeFL = new GregorianCalendar(TimeZone.getTimeZone("Europe/Helsinki"));
        timeFL.set(2022, 12-1, 30, 00, 00, 00);

        Calendar timeDE = new GregorianCalendar(TimeZone.getTimeZone("Europe/Berlin"));
        timeDE.setTimeInMillis(timeFL.getTimeInMillis());

        System.out.println("Time in Finland: " + toString(timeFL));
        System.out.println("Time in Germany: " + toString(timeDE));

        assertNotEquals(toString(timeFL), toString(timeDE));
        assertFalse(timeFL.after(timeDE));
        assertFalse(timeDE.after(timeFL));
    }

    @Test
    public void formatNumber_inDifferentLocales_giveExpectedResults()
    {
        NumberFormat nf_us = NumberFormat.getInstance(Locale.US);
        NumberFormat nf_de = NumberFormat.getInstance(Locale.GERMANY);

        nf_us.setMaximumFractionDigits(2);
        nf_de.setMaximumFractionDigits(2);

        double d = (double)3/7; 

        assertEquals("0.43", nf_us.format(d));
        assertEquals("0,43", nf_de.format(d));
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
