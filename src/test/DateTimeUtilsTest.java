package test;

import data.sqliteUtils.DateTimeUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtilsTest {
    @Test
    public void dateTest() {
        String date = "2017-04-08";
        LocalDate localDate = DateTimeUtils.stringDateToJavaDate(date);
        assert date.equals(DateTimeUtils.JavaDateToStringDate(localDate));
    }

    @Test
    public void dateTimeTest() {
        String dateTime = "2017-04-08 14:00:00";
        LocalDateTime localDateTime = DateTimeUtils.stringDateTimeToJavaDateTime(dateTime);
        assert dateTime.equals(DateTimeUtils.JavaDateTimeToStringDateTime(localDateTime));
    }
}
