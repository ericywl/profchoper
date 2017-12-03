package profchoper.course;

import profchoper.slot.Slot;
import profchoper.user.Professor;
import profchoper.user.Student;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CourseSlots {
    private final Course course;
    private Map<Professor, List<Slot>> profSlotsMap = new HashMap<>();

    public CourseSlots(Course course) {
        this.course = course;
    }

    public void addNewProf(Professor prof) {
        profSlotsMap.putIfAbsent(prof, new ArrayList<>());
    }

    public void removeProf(Professor prof) {
        profSlotsMap.remove(prof);
    }

    // Check if the timing is valid ie. within 8am to 5pm
    // For every 30 minutes interval, addSlot
    public void addSlotPeriod(Professor prof, DayOfWeek day, LocalTime periodStart, LocalTime periodEnd) {
        if (!periodStart.isBefore(periodEnd)) {
            System.out.println("Error: Start time cannot be before end time.");
            return;
        }

        if (periodStart.isBefore(LocalTime.of(8, 0)) || periodStart.isAfter(LocalTime.of(17, 0))
                || periodEnd.isAfter(LocalTime.of(17, 0))) {
            System.out.println("Error: Slots can only be from 8am to 5pm.");
            return;
        }

        long minutesBetween = ChronoUnit.MINUTES.between(periodStart, periodEnd);
        for (int i = 0; i < minutesBetween; i += 30) {
            LocalTime startTime = periodStart.plus(i, ChronoUnit.MINUTES);
            Slot slot = new Slot(day, startTime);
            addSlot(prof, slot);
        }
    }

    // Adds the slot to the map if prof exist in map and slot does not exist list
    private void addSlot(Professor prof, Slot slot) {
        List<Slot> slotList = profSlotsMap.getOrDefault(prof, null);
        if (slotList == null) {
            System.out.println("Error: Slot list for " + prof + " is null.");
            return;
        }

        if (slotList.contains(slot)) {
            System.out.println("Error: Slot already exists in " + prof + "'s list.");
            return;
        }

        slotList.add(slot);
    }

    // Book a particular slot from the prof
    private boolean addBooking(Professor prof, Student student, Slot bookSlot) {
        List<Slot> slotList = profSlotsMap.getOrDefault(prof, null);
        if (slotList == null) {
            System.out.println("Error: Slot list for " + prof + " is null.");
            return false;
        }

        for (Slot slot : slotList) {
            if (slot.equals(bookSlot)) {
                slot.addBooking(student);
                return true;
            }
        }

        System.out.println("Error: Slot not found in " + prof + "'s list.");
        return false;
    }

    public Course getCourse() {
        return course;
    }

    public Map<Professor, List<Slot>> getProfSlotsMap() {
        return profSlotsMap;
    }
}
