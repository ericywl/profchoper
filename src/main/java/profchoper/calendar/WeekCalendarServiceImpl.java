package profchoper.calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profchoper.bookingSlot.BookingSlot;
import profchoper.bookingSlot.BookingSlotService;
import sun.security.x509.AVA;

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
        List<List<String>> matrix = createProfCalMatrix(profAlias, startDateOfSchoolWeek, STUDENT);

        return new WeekCalendar(startDateOfSchoolWeek, matrix);
    }

    @Override
    public WeekCalendar getProfCalendar(String profAlias, LocalDate startDateOfSchoolWeek) {
        List<List<String>> matrix = createProfCalMatrix(profAlias, startDateOfSchoolWeek, PROF);

        return new WeekCalendar(startDateOfSchoolWeek, matrix);
    }


    private List<List<String>> createCourseCalMatrix(int courseId, LocalDate startDateOfSchoolWeek) {
        List<BookingSlot> slotList = slotService.getSlotsByCourseAndSWeek(courseId, startDateOfSchoolWeek);
        List<List<String>> output = new ArrayList<>();
        List<String> temp;

        for (int i = 0; i < WEEK_CAL_ROW; i++) {
            LocalTime timePart = ROW_TO_TIME.get(i);
            String timeRange = timeRangeFormat(timePart);

            temp = new ArrayList<>();
            temp.add(timeRange);

            for (int j = 0; j < WEEK_CAL_COL; j++) {
                LocalDate datePart = startDateOfSchoolWeek.plus(j, ChronoUnit.DAYS);
                LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);

                temp.add(createCourseCalString(dateTime, slotList));
            }

            output.add(temp);
        }

        return output;
    }

    private String createCourseCalString(LocalDateTime dateTime, List<BookingSlot> slotList) {
        StringBuilder outputBld = new StringBuilder();

        for (BookingSlot slot : slotList) {
            if (slot.getDateTime().equals(dateTime) && slot.getBookStatus().equals(AVAIL)) {
                outputBld.append(slot.getProfAlias().toUpperCase());
                outputBld.append(", ");
            }
        }

        String output = outputBld.toString();
        int len = output.length();

        if (len == 0) return "";

        return output.substring(0, len - 2);
    }


    private List<List<String>> createProfCalMatrix(String profAlias, LocalDate startDateOfSchoolWeek, String userType) {
        List<BookingSlot> slotList = slotService.getSlotsByProfAndSWeek(profAlias, startDateOfSchoolWeek);
        List<List<String>> output = new ArrayList<>();
        List<String> temp;

        for (int i = 0; i < WEEK_CAL_ROW; i++) {
            LocalTime timePart = ROW_TO_TIME.get(i);
            String timeRange = timeRangeFormat(timePart);

            temp = new ArrayList<>();
            temp.add(timeRange);

            for (int j = 0; j < WEEK_CAL_COL; j++) {
                LocalDate datePart = startDateOfSchoolWeek.plus(j, ChronoUnit.DAYS);
                LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);

                temp.add(createProfCalString(dateTime, slotList, userType));
            }

            output.add(temp);
        }

        return output;
    }

    private String createProfCalString(LocalDateTime dateTime, List<BookingSlot> slotList, String userType) {
        StringBuilder outputBld = new StringBuilder();

        for (BookingSlot slot : slotList) {
            if (slot.getDateTime().equals(dateTime)) {
                String appendedStr = "";
                if (userType.equals(STUDENT))
                    appendedStr = slot.getBookStatus().equals(AVAIL)
                            ? slot.getProfAlias().toUpperCase() : "";
                else if (userType.equals(PROF))
                    appendedStr = slot.getBookStatus().toUpperCase();

                outputBld.append(appendedStr);
            }
        }

        return outputBld.toString();
    }

    private String timeRangeFormat(LocalTime timePart) {
        LocalTime timePart2 = timePart.plus(30, ChronoUnit.MINUTES);

        return timePart.format(dtf) + " - " + timePart2.format(dtf);
    }

}
