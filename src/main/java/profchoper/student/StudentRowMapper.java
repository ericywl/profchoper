package profchoper.student;

import org.springframework.jdbc.core.RowMapper;
import profchoper.course.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Extracts results from SQL queries and return student
public class StudentRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        int studentId = resultSet.getInt("s_id");
        String studentName = resultSet.getString("s_name");
        String studentEmail = resultSet.getString("s_email");

        List<Course> courseList = new ArrayList<>();
        for (int j = 1; j < 5; j++) {
            String courseId = resultSet.getString("c" + j + "_id");
            String courseName = resultSet.getString("c" + j + "_name");
            String courseAlias = resultSet.getString("c" + j + "_alias");

            Course course = new Course(courseId, courseName, courseAlias);
            courseList.add(course);
        }

        return new Student(studentId, studentName, studentEmail, courseList);
    }
}
