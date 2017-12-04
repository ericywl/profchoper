package profchoper.course;

import profchoper.user.Professor;
import profchoper.user.Student;

import java.util.HashSet;
import java.util.Set;

public class Course {
    private final String courseId;
    private final String courseName;
    private Set<Professor> professorList = new HashSet<>();
    private Set<Student> enrolledStudents = new HashSet<>();

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public void addProfessor(Professor prof) {
        professorList.add(prof);
    }

    public void removeProfessor(Professor prof) {
        professorList.remove(prof);
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
    }

    public void dropStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Set<Professor> getProfessorList() {
        return professorList;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }
}
