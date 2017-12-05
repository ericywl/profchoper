package profchoper.user;


import profchoper.course.Course;

public class Professor {
    private final String name;
    private final String email;
    private String alias;
    private String officeLocation;
    private Course course = null;

    public Professor(String name, String email, String officeLocation) {
        this.name = name;
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

    public void setAlias(String alias) {
        this.alias = alias;
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
}
