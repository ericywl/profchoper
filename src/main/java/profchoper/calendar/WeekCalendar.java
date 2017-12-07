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
public class WeekCalendar {

    @Autowired
    private SlotService slotService;

    public List<List<String>> getWeekCalendar(LocalDate startDateOfWeek) {
        List<List<String>> output = new ArrayList<>();
        List<String> temp = null;

        for (int i = 0; i < WEEK_CAL_ROW; i++) {
            for (int j = 0; j < WEEK_CAL_COL; j++) {
                LocalDate datePart = startDateOfWeek.plus(j, ChronoUnit.DAYS);
                LocalTime timePart = ROW_TO_TIME.get(i);
                LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);

                temp = new ArrayList<>();
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
        outputBld.append("<br />");

        for (Slot slot : slotList) {
            outputBld.append(slot.getProfAlias());
            outputBld.append("\n");
        }

        return outputBld.toString();
    }
}
