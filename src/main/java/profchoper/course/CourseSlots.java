package profchoper.course;

import profchoper.slot.Slot;
import profchoper.slot.SlotPeriod;
import profchoper.user.Professor;
import profchoper.user.Student;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static profchoper._misc.Constant.*;

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

    public boolean addSlotPeriod(Professor prof, SlotPeriod period) {
        List<Slot> slotList = profSlotsMap.getOrDefault(prof, null);
        if (slotList == null) {
            System.out.println("Error: Slot list for " + prof + " is null.");
            return false;
        }

        // For every 30 minutes interval, add slot to list if it is not already in list
        for (int i = 0; i < period.getDuration(); i += SLOT_TIME) {
            LocalTime slotStart = period.getStartTime().plus(i, ChronoUnit.MINUTES);
            Slot slot = new Slot(period.getDay(), slotStart);
            if (slotList.contains(slot)) {
                System.out.println("Error: Slot already in " + prof + "'s list.");
                continue;
            }

            slotList.add(slot);
        }

        profSlotsMap.replace(prof, slotList);
        return true;
    }

    // Book a particular slot from the prof
    private boolean addBooking(Student student, Professor prof, Slot bookSlot) {
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
