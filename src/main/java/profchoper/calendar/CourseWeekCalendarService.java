package profchoper.calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import profchoper.slot.Slot;
import profchoper.slot.SlotService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.ROW_TO_TIME;
import static profchoper._misc.Constant.WEEK_CAL_COL;
import static profchoper._misc.Constant.WEEK_CAL_ROW;

@Service
public class CourseWeekCalendarService {

    @Autowired
    private SlotService slotService;

    public List<List<String>> getWeekCalendar(LocalDate startDateOfWeek) {
        List<List<String>> output = new ArrayList<>();
        List<String> temp;

        for (int i = 0; i < WEEK_CAL_ROW; i++) {
            temp = new ArrayList<>();
            for (int j = 0; j < WEEK_CAL_COL; j++) {
                LocalDate datePart = startDateOfWeek.plus(j, ChronoUnit.DAYS);
                LocalTime timePart = ROW_TO_TIME.get(i);
                LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);

                temp.add(getProfAliasesForHTML(dateTime));
            }

            output.add(temp);
        }

        return output;
    }

    private String getProfAliasesForHTML(LocalDateTime dateTime) {
        List<Slot> slotList = slotService.getSlotsByDateTime(dateTime);
        if (slotList == null) return "\n";

        StringBuilder outputBld = new StringBuilder();
        outputBld.append("\n");

        for (Slot slot : slotList) {
            outputBld.append(slot.getProfAlias());
            outputBld.append("\n");
        }

        return outputBld.toString();
    }
}
