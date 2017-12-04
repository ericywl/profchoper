package profchoper.user;

import profchoper.course.Course;

import java.io.Serializable;


public class Student implements Serializable {
    private int id;
    private String name;
    private Course[] enrolledCourses;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Course[] getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(Course[] enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return name;
    }
}
