package utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;

public class CalendarUtils {
    public static LocalDateTime xmlGregorianCalendarToLocalDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
        return LocalDateTime.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(), xmlGregorianCalendar.getDay(), xmlGregorianCalendar.getHour(), xmlGregorianCalendar.getMinute(), xmlGregorianCalendar.getSecond(), xmlGregorianCalendar.getMillisecond() * 1000000);
    }

    public static LocalDate xmlGregorianCalendarToLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
        return LocalDate.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(), xmlGregorianCalendar.getDay());
    }

    public static XMLGregorianCalendar localDateTimeToXMLGregorianCalendar(LocalDateTime localDateTime) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(localDateTime.getYear(), localDateTime.getMonth().getValue() - 1, localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()));
    }

    public static XMLGregorianCalendar localDateToXMLGregorianCalendar(LocalDate localDate) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(localDate.getYear(), localDate.getMonth().getValue() - 1, localDate.getDayOfMonth()));
    }
}
