package profchoper.calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profchoper.bookingSlot.BookingSlot;
import profchoper.bookingSlot.BookingSlotService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.*;

@Service
public class WeekCalendarServiceImpl implements WeekCalendarService {
    @Autowired
    private BookingSlotService slotService;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public WeekCalendar getStudentCalendarByCourse(int courseId, LocalDate startDateOfSchoolWeek) {
        List<List<String>> matrix = createCourseCalMatrix(courseId, startDateOfSchoolWeek);
        return new WeekCalendar(startDateOfSchoolWeek, matrix);
    }

    @Override
    public WeekCalendar getStudentCalendarByProf(String profAlias, LocalDate startDateOfSchoolWeek) {
        return null;
    }

    @Override
    public WeekCalendar getProfCalendar(String profAlias, LocalDate startDateOfSchoolWeek) {
        return null;
    }

    private List<List<String>> createCourseCalMatrix(int courseId, LocalDate startDateOfWeek) {
        List<BookingSlot> slotList = slotService.getSlotsByCourseAndSWeek(courseId, startDateOfWeek);
        List<List<String>> output = new ArrayList<>();
        List<String> temp;

        for (int i = 0; i < WEEK_CAL_ROW; i++) {
            LocalTime timePart = ROW_TO_TIME.get(i);
            LocalTime timePart2 = timePart.plus(30, ChronoUnit.MINUTES);
            String timeRange = timePart.format(dtf) + " - " + timePart2.format(dtf);

            temp = new ArrayList<>();
            temp.add(timeRange);

            for (int j = 0; j < WEEK_CAL_COL; j++) {
                LocalDate datePart = startDateOfWeek.plus(j, ChronoUnit.DAYS);
                LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);

                temp.add(createCourseCalString(dateTime, slotList));
            }

            output.add(temp);
        }

        return output;
    }

    private String createCourseCalString(LocalDateTime dateTime, List<BookingSlot> slotList) {
        StringBuilder outputBld = new StringBuilder();

        for (int i = 0, size = slotList.size(); i < size; i++) {
            BookingSlot slot = slotList.get(i);
            if (slot.getDateTime().equals(dateTime)) {
                outputBld.append(slot.getProfAlias());

                if (i < size - 1) outputBld.append(", ");
            }
        }

        return outputBld.toString();
    }

}