package profchoper.slot;

import profchoper.user.Student;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static profchoper._misc.Constant.SLOT_TIME;

public class Slot {
    private final DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Student> studentList = new ArrayList<>();

    public Slot(DayOfWeek day, LocalTime startTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = startTime.plus(SLOT_TIME, ChronoUnit.MINUTES);
    }

    public void addBooking(Student student) {
        studentList.add(student);
    }

    public void cancelBooking(Student student) {
        studentList.remove(student);
    }

    @Override
    public String toString() {
        return day + ": " + startTime + " => " + endTime;
    }

    @Override
    // Slots are considered equal if they have the same day and startTime
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;

        Slot comparedSlot = (Slot) obj;

        if (!comparedSlot.getDay().equals(this.getDay()))
            return false;

        if (!comparedSlot.getStartTime().equals(this.getStartTime()))
            return false;

        return true;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
