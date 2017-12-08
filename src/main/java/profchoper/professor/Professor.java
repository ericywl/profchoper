package profchoper.professor;


import profchoper.course.Course;

import javax.persistence.*;

@Entity
public class Professor {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "alias")
    private String alias;

    @Column(name = "office")
    private String officeLocation;

    @OneToOne(cascade = CascadeType.ALL)
    private Course course;

    public Professor() {
        // empty constructor
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
}
