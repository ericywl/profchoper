package profchoper.calendar;

import profchoper.slot.Slot;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static profchoper._misc.Constant.WEEK_CAL_COL;

public class WeekCalendarTimeRow {
    private WeekCalendarSlotHandler[] slotHandlerArray = new WeekCalendarSlotHandler[WEEK_CAL_COL];

    public WeekCalendarTimeRow(LocalTime time) {
        for (int i = 0; i < WEEK_CAL_COL; i++) {
            String dayOfWeekStr = DayOfWeek.of(i + 1).toString();
            slotHandlerArray[i] = new WeekCalendarSlotHandler(dayOfWeekStr + "_" + time.toString());
        }
    }

    public void insertSlotToDay(DayOfWeek dayOfWeek, Slot slot) {
        slotHandlerArray[dayOfWeek.getValue() - 1].addSlot(slot);
    }

    public WeekCalendarSlotHandler getMonSlotHandler() {
        return slotHandlerArray[0];
    }

    public WeekCalendarSlotHandler getTueSlowHandler() {
        return slotHandlerArray[1];
    }

    public WeekCalendarSlotHandler getWedSlotHandler() {
        return slotHandlerArray[2];
    }

    public WeekCalendarSlotHandler getThuSlotHandler() {
        return slotHandlerArray[3];
    }

    public WeekCalendarSlotHandler getFriSlotHandler() {
        return slotHandlerArray[4];
    }
}
