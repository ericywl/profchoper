package profchoper.student;


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

    @ElementCollection
    private List<Integer> courseIds;

    public Student() {
        // empty constructor
    }

    public Student(int id, String name, String email, List<Integer> courseIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courseIds = courseIds;
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

    public List<Integer> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Integer> courseIds) {
        this.courseIds = courseIds;
    }
}
