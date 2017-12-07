package profchoper.student;


import profchoper.course.Course;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Student {
    @Id
    private int id;

    private String name;
    private String email;
    private List<Course> enrolledCourses;

    public Student(int id, String name, String email, List<Course> enrolledCourses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
