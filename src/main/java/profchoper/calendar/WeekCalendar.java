package profchoper.calendar;

import profchoper.slot.Slot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeekCalendar {
    private LocalDate displayDate;
    private List<LocalTime> timeList = new ArrayList<>();
    private HashMap<LocalTime, Integer> timeHash = new HashMap<>();
    private HashMap<DayOfWeek, Integer> dayHash = new HashMap<>();
    private WeekCalendarSlotHandler[][] slotHandlerMatrix = new WeekCalendarSlotHandler[5][16];;

    public WeekCalendar(LocalDate date) {
        this.displayDate = date;

        timeHash.put(LocalTime.of(9, 0, 0), 0);
        timeHash.put(LocalTime.of(9, 30, 0), 1);
        timeHash.put(LocalTime.of(10, 0, 0), 2);
        timeHash.put(LocalTime.of(10, 30, 0), 3);
        timeHash.put(LocalTime.of(11, 0, 0), 4);
        timeHash.put(LocalTime.of(11, 30, 0), 5);
        timeHash.put(LocalTime.of(12, 0, 0), 6);
        timeHash.put(LocalTime.of(12, 30, 0), 7);
        timeHash.put(LocalTime.of(13, 0, 0), 8);
        timeHash.put(LocalTime.of(13, 30, 0), 9);
        timeHash.put(LocalTime.of(14, 0, 0), 10);
        timeHash.put(LocalTime.of(14, 30, 0), 11);
        timeHash.put(LocalTime.of(15, 0, 0), 12);
        timeHash.put(LocalTime.of(15, 30, 0), 13);
        timeHash.put(LocalTime.of(16, 0, 0), 14);
        timeHash.put(LocalTime.of(16, 30, 0), 15);

        dayHash.put(DayOfWeek.MONDAY, 0);
        dayHash.put(DayOfWeek.TUESDAY, 1);
        dayHash.put(DayOfWeek.WEDNESDAY, 2);
        dayHash.put(DayOfWeek.THURSDAY, 3);
        dayHash.put(DayOfWeek.FRIDAY, 4);

        for (int i = 0; i < slotHandlerMatrix.length; i++) {
            for (int j = 0; j < slotHandlerMatrix[i].length; j++) {
                String handlerID = i + "_" + j;
                slotHandlerMatrix[i][j] = new WeekCalendarSlotHandler(handlerID);
            }
        }
    }

    public void insertSlots(List<Slot> slotList) {
        for (Slot slot : slotList)
            insertSlot(slot);
    }

    private void insertSlot(Slot slot) {
        LocalTime time = slot.getStartDateTime().toLocalTime();
        DayOfWeek day = slot.getDayOfWeek();

        WeekCalendarSlotHandler slotHandler = slotHandlerMatrix[dayHash.get(day)][timeHash.get(time)];
        slotHandler.addSlot(slot);

        if (slotHandler.getDateTime() == null)
            slotHandler.setDateTime(slot.getStartDateTime());

    }

    public LocalDate getDisplayDate() {
        return displayDate;
    }

    public List<LocalTime> getTimeList() {
        return timeList;
    }

    public WeekCalendarSlotHandler[][] getSlotHandlerMatrix() {
        return slotHandlerMatrix;
    }
}
