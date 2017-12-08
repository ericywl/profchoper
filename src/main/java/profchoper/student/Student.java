package profchoper.student;


import profchoper.course.Course;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany
    private List<Course> enrolledCourses;

    public Student() {
        // default constructor
    }

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
