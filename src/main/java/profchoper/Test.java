package profchoper;

import profchoper.user.Professor;
import profchoper.user.Student;

public class Test {
    public static void main(String[] args) {
        Professor oka = new Professor("Oka Kurniawan", "1.502.27");
        Professor zy = new Professor("Zhang Yue", "1.702.34");

        Student eric = new Student(1002394, "Eric");

        // System.out.println(eric.getEnrolledCourses());


    }
}
