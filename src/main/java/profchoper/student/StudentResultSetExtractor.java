package profchoper.student;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import profchoper.course.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setEmail(resultSet.getString("email"));

        List<Course> courseList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String courseId = resultSet.getString("c" + i + "_id");
            String courseName = resultSet.getString("c" + i + "_name");
            String courseAlias = resultSet.getString("c" + i + "_alias");

            Course course = new Course(courseId, courseName, courseAlias);
            courseList.add(course);
        }

        student.setEnrolledCourses(courseList);

        return student;
    }
}
