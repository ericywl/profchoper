package profchoper.calendar;

import profchoper.course.Course;
import profchoper.course.CourseSlots;

import java.util.HashMap;
import java.util.Map;

public class Calendar {
    private Map<Course, CourseSlots> courseSlotsMap = new HashMap<>();

    public void addCourseSlots(CourseSlots courseSlots) {
        courseSlotsMap.putIfAbsent(courseSlots.getCourse(), courseSlots);
    }

    public void displayCourse(Course course) {
        CourseSlots courseSlots = courseSlotsMap.get(course);
        System.out.println(courseSlots.getProfSlotsMap());
    }
}
