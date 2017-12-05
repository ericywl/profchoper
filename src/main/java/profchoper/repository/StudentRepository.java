package profchoper.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import profchoper.course.Course;
import profchoper.user.Student;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    private CourseRepository courseRepository = new CourseRepository();

    public List<Student> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        List<Student> studentList = new ArrayList<>();

        String selectSQL = "SELECT * FROM students ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            int studentId = rs.getInt("id");
            String studentName = rs.getString("name");
            String studentEmail = rs.getString("email");
            List<Course> courseList = new ArrayList<>();

            for (int i = 1; i < 5; i++) {
                String courseId = rs.getString("course" + i);
                courseList.add(courseRepository.findById(courseId));
            }

            Student student = new Student(studentId, studentName, studentEmail, courseList);
            studentList.add(student);
        }

        return studentList;
    }

    public Student findById(int id) throws SQLException {
        return findBy("id", "INTEGER", id);
    }

    private <T> Student findBy(String selection, String type, T arg) throws SQLException {
        Connection connection = dataSource.getConnection();
        Student student = null;

        String selectSQL = "SELECT * FROM students " +
                "WHERE " + selection + " = " + arg + "::" + type;
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            int studentId = rs.getInt("id");
            String studentName = rs.getString("name");
            String studentEmail = rs.getString("email");
            List<Course> courseList = new ArrayList<>();

            for (int i = 1; i < 5; i++) {
                String courseId = rs.getString("course" + i);
                courseList.add(courseRepository.findById(courseId));
            }

            student = new Student(studentId, studentName, studentEmail, courseList);
        }

        return student;
    }
}
