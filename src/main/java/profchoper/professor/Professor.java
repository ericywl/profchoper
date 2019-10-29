package profchoper.professor;


import profchoper.course.Course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Professor {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "alias")
    private String alias;

    @Column(name = "email")
    private String email;

    @Column(name = "office")
    private String office;

    @OneToOne
    private Course course;


    public Professor() {
        // default constructor
    }

    public Professor(String name, String alias, String email, String office, Course course) {
        this.name = name;
        this.email = email;
        this.alias = alias;
        this.office = office;
        this.course = course;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;

        Professor comparedProf = (Professor) obj;

        if (!comparedProf.alias.equals(this.alias)) return false;

        if (!comparedProf.name.equals(this.name)) return false;

        if (!comparedProf.email.equals(this.email)) return false;

        return true;
    }

    public String getEmail() {
        return email;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getOffice() {
        return office;
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

    public void setOffice(String office) {
        this.office = office;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
