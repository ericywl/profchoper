package profchoper.calendar;

import profchoper.slot.Slot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Calendar {
    private LocalDate displayDate;
    private List<LocalTime> timeList = new ArrayList<>();
    private HashMap<LocalTime, Integer> timeHash = new HashMap<>();
    private HashMap<DayOfWeek, Integer> dayHash = new HashMap<>();
    private CalendarSlotHandler[][] calendarMatrix;

    public Calendar(LocalDate date) {
        timeHash.put(LocalTime.parse("8:00:00"), 0);
        timeHash.put(LocalTime.parse("8:30:00"), 1);
        timeHash.put(LocalTime.parse("9:00:00"), 2);
        timeHash.put(LocalTime.parse("9:30:00"), 3);
        timeHash.put(LocalTime.parse("10:00:00"), 4);
        timeHash.put(LocalTime.parse("10:30:00"), 5);
        timeHash.put(LocalTime.parse("11:00:00"), 6);
        timeHash.put(LocalTime.parse("11:30:00"), 7);
        timeHash.put(LocalTime.parse("12:00:00"), 8);
        timeHash.put(LocalTime.parse("12:30:00"), 9);
        timeHash.put(LocalTime.parse("1:00:00"), 10);
        timeHash.put(LocalTime.parse("1:30:00"), 11);
        timeHash.put(LocalTime.parse("2:00:00"), 12);
        timeHash.put(LocalTime.parse("2:30:00"), 13);
        timeHash.put(LocalTime.parse("3:00:00"), 14);
        timeHash.put(LocalTime.parse("3:30:00"), 15);
        timeHash.put(LocalTime.parse("4:00:00"), 16);
        timeHash.put(LocalTime.parse("4:30:00"), 17);

        dayHash.put(DayOfWeek.MONDAY, 0);
        dayHash.put(DayOfWeek.TUESDAY, 0);
        dayHash.put(DayOfWeek.WEDNESDAY, 0);
        dayHash.put(DayOfWeek.THURSDAY, 0);
        dayHash.put(DayOfWeek.FRIDAY, 0);

        timeList.add(LocalTime.parse("8:00:00"));
        timeList.add(LocalTime.parse("8:30:00"));
        timeList.add(LocalTime.parse("9:00:00"));
        timeList.add(LocalTime.parse("9:30:00"));
        timeList.add(LocalTime.parse("10:00:00"));
        timeList.add(LocalTime.parse("10:30:00"));
        timeList.add(LocalTime.parse("11:00:00"));
        timeList.add(LocalTime.parse("11:30:00"));
        timeList.add(LocalTime.parse("12:00:00"));
        timeList.add(LocalTime.parse("12:30:00"));
        timeList.add(LocalTime.parse("1:00:00"));
        timeList.add(LocalTime.parse("1:30:00"));
        timeList.add(LocalTime.parse("2:00:00"));
        timeList.add(LocalTime.parse("2:30:00"));
        timeList.add(LocalTime.parse("3:00:00"));
        timeList.add(LocalTime.parse("3:30:00"));
        timeList.add(LocalTime.parse("4:00:00"));
        timeList.add(LocalTime.parse("4:30:00"));

        calendarMatrix = new CalendarSlotHandler[5][18];
        displayDate = date;
    }

    private void insertSlot(Slot slot) {
        LocalTime localTime = slot.getStartDateTime().toLocalTime();

        calendarMatrix[dayHash.get(slot.getDay())]
                [timeHash.get(localTime)].addSlot(slot);

        calendarMatrix[dayHash.get(slot.getDay())]
                [timeHash.get(localTime)].setDateTime(slot.getStartDateTime());

    }

    public LocalDate getDisplayDate() {
        return displayDate;
    }

    public List<LocalTime> getTimeList() {
        return timeList;
    }

    public CalendarSlotHandler[][] getCalendarMatrix() {
        return calendarMatrix;
    }
}
