package data.sqliteUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDate stringDateToJavaDate(String databaseDate) {
        return LocalDate.parse(databaseDate, dateFormatter);
    }

    public static LocalDateTime stringDateTimeToJavaDateTime(String databaseDateTime) {
        return LocalDateTime.parse(databaseDateTime, dateTimeFormatter);
    }

    public static String JavaDateToStringDate(LocalDate date) {
        return date.format(dateFormatter);
    }

    public static String JavaDateTimeToStringDateTime(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }
}
