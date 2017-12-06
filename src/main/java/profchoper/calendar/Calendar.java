package profchoper.calendar;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Calendar {
    private LocalDateTime dateTime;
    private DayOfWeek dayOfWeek;

    public static void main(String[] args) {
        DayOfWeek dow = DayOfWeek.MONDAY;
        System.out.println(dow);
    }
}
