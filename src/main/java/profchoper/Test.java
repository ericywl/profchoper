package profchoper;

import profchoper.course.Course;
import profchoper.course.CourseSlots;
import profchoper.user.Professor;
import profchoper.user.Student;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Test {
    public static void main(String[] args) {
        Professor oka = new Professor("Oka Kurniawan", "1.502.27");
        Professor zy = new Professor("Zhang Yue", "1.702.34");

        CourseSlots courseSlots = new CourseSlots(Course.COMP_STRUCT);
        courseSlots.addNewProf(oka);
        courseSlots.addNewProf(zy);

        courseSlots.addSlotPeriod(oka, DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(15, 0));

        Student eric = new Student(1002394, "Eric");

        // System.out.println(eric.getEnrolledCourses());
        System.out.println(courseSlots.getProfSlotsMap());

    }
}
