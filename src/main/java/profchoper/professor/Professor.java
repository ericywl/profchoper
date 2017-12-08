package profchoper.professor;


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
    private String office;

    @Column(name = "course_id")
    private int courseId;

    public Professor() {
        // empty constructor
    }

    public Professor(String name, String email, String alias, String office, int courseId) {
        this.name = name;
        this.email = email;
        this.alias = alias;
        this.office = office;
        this.courseId = courseId;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
