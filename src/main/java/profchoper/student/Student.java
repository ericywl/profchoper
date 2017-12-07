package profchoper.student;


import profchoper.course.Course;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {
    @Id
    private int id;
    private String name;
    private String email;

    @OneToMany
    @JoinColumn(name = "course_id")
    private List<Course> enrolledCourses;

    public Student() {
        // empty
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



    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
