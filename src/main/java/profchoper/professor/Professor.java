package profchoper.professor;


import profchoper.course.Course;

public class Professor {
    private final String name;
    private final String email;
    private String alias;
    private String officeLocation;
    private Course course;

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
