package test;

import org.junit.jupiter.api.Test;
import utils.CalendarUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalendarUtilsTest {
    @Test
    public void dateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        try {
            XMLGregorianCalendar xmlGregorianCalendar = CalendarUtils.localDateTimeToXMLGregorianCalendar(localDateTime);
            LocalDateTime newDateTime = CalendarUtils.xmlGregorianCalendarToLocalDateTime(xmlGregorianCalendar);
            long interval = newDateTime.until(localDateTime, ChronoUnit.SECONDS);
            assert interval <= 1 && interval >= 0;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateTest() {
        LocalDate localDate = LocalDate.now();
        try {
            XMLGregorianCalendar xmlGregorianCalendar = CalendarUtils.localDateToXMLGregorianCalendar(localDate);
            LocalDate newDate = CalendarUtils.xmlGregorianCalendarToLocalDate(xmlGregorianCalendar);
            assert newDate.isEqual(localDate);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }
}
