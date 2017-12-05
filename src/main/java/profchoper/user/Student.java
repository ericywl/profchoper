package profchoper.user;


import profchoper.course.Course;

public class Student {
    private final int id;
    private final String name;
    private final String email;
    private Course[] enrolledCourses;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Student(int id, String name, String email, Course[] enrolledCourses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.enrolledCourses = enrolledCourses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setEnrolledCourses(Course[] enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public Course[] getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
