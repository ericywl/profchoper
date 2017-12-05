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

@Repository("courseRepo")
public class CourseRepository {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    public List<Course> findAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        List<Course> courseList = new ArrayList<>();

        String selectSQL = "SELECT * FROM courses ORDER BY id";
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

    public Course findById(String id) throws SQLException {
        Connection connection = dataSource.getConnection();

        String selectSQL = "SELECT * FROM courses WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        return findBy(rs);
    }

    private Course findBy(ResultSet rs) throws SQLException {
        Course course = null;

        if (rs.next()) {
            String courseId = rs.getString("id");
            String courseName = rs.getString("name");
            String courseAlias = rs.getString("alias");

            course = new Course(courseId, courseName, courseAlias);
        }

        return course;
    }
}
