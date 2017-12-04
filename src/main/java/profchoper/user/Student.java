package profchoper.user;

import profchoper.course.Course;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {
    private final int id;
    private final String name;
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return name;
    }
}
