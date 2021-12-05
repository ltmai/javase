package mai.linh.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

class DateTimeTest {

    @Test
    public void sameDate_createdDifferently_datesEqual() {
        var date1 = LocalDate.of(2020, 02, 20);        
        var date2 = LocalDate.parse("2020-02-20");

        assertEquals(date1, date2);
    }

    @Test
    public void today_addDays_getTomorrow() {
        var today = LocalDate.now();
        var tomorrow = today.plusDays(1);

        assertTrue((tomorrow.getDayOfYear() == today.getDayOfYear()+1) || 
                   (tomorrow.getYear() == today.getYear()+1));

        assertTrue(today.isBefore(tomorrow));
        assertTrue(tomorrow.isAfter(today));
    }

    @Test
    public void leapYear_checkLearYear_returnTrue() {
        assertTrue(LocalDate.of(2020, 02, 20).isLeapYear());
    }

    @Test
    public void boundaryFunctions_onLocalDateTime_returnBoundaryValues() {
        LocalDateTime beginningOfDay = LocalDate.now().atStartOfDay();
        LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());

        assertEquals(0, beginningOfDay.getHour());
        assertEquals(1, firstDayOfMonth.getDayOfMonth());
    }
}
