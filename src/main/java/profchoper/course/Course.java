package profchoper.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "alias")
    private String alias;


    public Course() {
        // default constructor
    }

    public Course(String id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    @Override
    public String toString() {
        return id.substring(0, 2) + "." + id.substring(2, 5) + " " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) return false;

        Course comparedCourse = (Course) obj;

        if (!comparedCourse.id.equalsIgnoreCase(this.id)) return false;

        if (!comparedCourse.name.equalsIgnoreCase(this.name)) return false;

        if (!comparedCourse.alias.equalsIgnoreCase(this.alias)) return false;

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
