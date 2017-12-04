package profchoper.user;

import profchoper.course.Course;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final int studentId;
    private final String studentName;
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(int studentId, String studentName) {
        this.studentName = studentName;
        this.studentId = studentId;
    }

    protected void enrollCourse(Course course) {
        enrolledCourses.add(course);
    }

    protected void dropCourse(Course course) {
        enrolledCourses.remove(course);
    }

    public String getStudentName() {
        return studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return studentName;
    }
}
