package profchoper.calendar;

import profchoper.slot.Slot;

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

public class WeekCalendar {
    private LocalDate startDate;
    private List<WeekCalendarTimeRow> timeRowList = new ArrayList<>();

    public WeekCalendar(LocalDate startDateOfSchoolWeek) {
        startDate = startDateOfSchoolWeek;

        for (int i = 0; i < WEEK_CAL_ROW; i++) {
            LocalTime time = DAY_FIRST_START_TIME.plus(i * 30, ChronoUnit.MINUTES);
            WeekCalendarTimeRow timeRow = new WeekCalendarTimeRow(time);
            timeRowList.add(timeRow);
        }
    }

    public void insertSlots(List<Slot> slotList) {
        for (Slot slot : slotList)
            insertSlot(slot);
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
}
