package profchoper.professor;


import profchoper.course.Course;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Professor {
    @Id
    private String name;

    private String alias;
    private String email;
    private String officeLocation;
    private Course course;

    public Professor() {
        // empty constructor
    }

    public Professor(String name, String email, String alias, String officeLocation, Course course) {
        this.name = name;
        this.email = email;
        this.alias = alias;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
