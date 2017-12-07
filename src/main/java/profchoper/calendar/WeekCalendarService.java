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
public class WeekCalendarService {

    @Autowired
    private BookingSlotService slotService;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    public List<List<String>> getCalendar(LocalDate startDateOfWeek) {
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

                temp.add(getListForHTML(dateTime));
            }

            output.add(temp);
        }

        return output;
    }

    private String getListForHTML(LocalDateTime dateTime) {
        List<BookingSlot> slotList = slotService.getSlotsByDateTime(dateTime);
        if (slotList == null) return " ";

        StringBuilder outputBld = new StringBuilder();

        for (int i = 0, size = slotList.size(); i < size; i++) {
            BookingSlot slot = slotList.get(i);
            outputBld.append(slot.getProfAlias().toUpperCase());

            if (i < size - 1) outputBld.append(", ");
        }

        return outputBld.toString();
    }
}
