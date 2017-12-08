package profchoper.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course {
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "alias")
    private String alias;


    public Course() {
        // default constructor
    }

    public Course(int id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    @Override
    public String toString() {
        String idStr = String.valueOf(id);
        if (idStr.length() == 4) idStr = "0" + idStr;

        idStr = idStr.substring(0, 2) + "." + idStr.substring(2,5);
        return  idStr + " " + name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
