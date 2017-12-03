package profchoper.course;

public enum Course {
    INFO_SYS("50.001", "Introduction to Information Systems & Programming"),
    COMP_STRUCT("50.002", "Computation Structures"),
    ALGO("50.004", "Introduction to Algorithms");

    public final String courseId;
    public final String courseName;

    Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }
}
