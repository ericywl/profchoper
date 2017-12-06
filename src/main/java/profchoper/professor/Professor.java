package profchoper.professor;


import profchoper.course.Course;

public class Professor {
    private String name;
    private String email;
    private String alias;
    private String officeLocation;
    private Course course = null;

    public Professor(String name, String alias, String email, String officeLocation) {
        this.name = name;
        this.alias = alias;
        this.email = email;
        this.officeLocation = officeLocation;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }
}
