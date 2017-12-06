package profchoper.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import profchoper.slot.Slot;
import profchoper.slot.SlotService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.DAY_FIRST_START_TIME;
import static profchoper._misc.Constant.TIME_TO_ROW;
import static profchoper._misc.Constant.WEEK_CAL_ROW;

@Component
public class WeekCalendar {
    @Autowired
    private SlotService slotService;

    private LocalDate startDate;
    private List<WeekCalendarTimeRow> timeRowList = new ArrayList<>();

    public void initialize(LocalDate startDate) {
        for (int i = 0; i < WEEK_CAL_ROW; i++) {
            LocalTime time = DAY_FIRST_START_TIME.plus(i * 30, ChronoUnit.MINUTES);
            WeekCalendarTimeRow timeRow = new WeekCalendarTimeRow(time);
            timeRowList.add(timeRow);
        }

        this.startDate = startDate;
        List<Slot> slotList = slotService.getSlotsBySchoolWeek(startDate);

        for (Slot slot : slotList) {
            insertSlot(slot);
        }
    }

    public void insertSlot(Slot slot) {
        LocalDateTime dateTime = slot.getStartDateTime();
        LocalTime time = dateTime.toLocalTime();
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();

        WeekCalendarTimeRow timeRow = timeRowList.get(TIME_TO_ROW.get(time));
        timeRow.insertSlotToDay(dayOfWeek, slot);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<WeekCalendarTimeRow> getTimeRowList() {
        return timeRowList;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setTimeRowList(List<WeekCalendarTimeRow> timeRowList) {
        this.timeRowList = timeRowList;
    }
}
