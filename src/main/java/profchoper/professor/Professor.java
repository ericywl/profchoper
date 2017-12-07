package profchoper.professor;


import profchoper.course.Course;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Professor {
    @Id
    private String name;
    private String email;
    private String alias;
    private String officeLocation;

    @ManyToOne
    private Course course;

    public Professor() {
        // empty
    }

    public Professor(String name, String alias, String email, String officeLocation, Course course) {
        this.name = name;
        this.alias = alias;
        this.email = email;
        this.officeLocation = officeLocation;
        this.course = course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAlias() {
        return alias;
    }

    public Course getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
