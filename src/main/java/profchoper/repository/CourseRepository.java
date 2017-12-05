package profchoper.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import profchoper.course.Course;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    public List<Course> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        List<Course> courseList = new ArrayList<>();

        String selectSQL = "SELECT * FROM courses";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            String courseId = rs.getString("id");
            String courseName = rs.getString("name");
            String courseAlias = rs.getString("alias");

            Course course = new Course(courseId, courseName, courseAlias);
            courseList.add(course);
        }

        return courseList;
    }

    public <T> Course findBy(String selection, T arg) throws SQLException {
        Connection connection = dataSource.getConnection();
        Course course = null;

        String selectSQL = "SELECT * FROM courses WHERE " + selection + " = " + arg;
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            String courseId = rs.getString("id");
            String courseName = rs.getString("name");
            String courseAlias = rs.getString("alias");

            course = new Course(courseId, courseName, courseAlias);
        }

        return course;
    }
}
