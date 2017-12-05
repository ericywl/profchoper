package profchoper.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import profchoper.course.Course;
import profchoper.course.CourseRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("studentRepo")
public class StudentRepository {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    private CourseRepository courseRepo;

    @Autowired
    public StudentRepository(CourseRepository courseRepository) {
        courseRepo = courseRepository;
    }

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
                String courseId = rs.getString("course" + i + "_id");
                courseList.add(courseRepo.findById(courseId));
            }

            Student student = new Student(studentId, studentName, studentEmail, courseList);
            studentList.add(student);
        }

        return studentList;
    }

    public Student findById(int id) throws SQLException {
        Connection connection = dataSource.getConnection();

        String selectSQL = "SELECT * FROM students WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        return findBy(rs);
    }

    public Student findByName(String name) throws SQLException {
        Connection connection = dataSource.getConnection();

        String selectSQL = "SELECT * FROM students WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();

        return findBy(rs);
    }

    private Student findBy(ResultSet rs) throws SQLException {
        Student student = null;

        if (rs.next()) {
            int studentId = rs.getInt("id");
            String studentName = rs.getString("name");
            String studentEmail = rs.getString("email");
            List<Course> courseList = new ArrayList<>();

            for (int i = 1; i < 5; i++) {
                String courseId = rs.getString("course" + i + "_id");
                courseList.add(courseRepo.findById(courseId));
            }

            student = new Student(studentId, studentName, studentEmail, courseList);
        }

        return student;
    }
}
