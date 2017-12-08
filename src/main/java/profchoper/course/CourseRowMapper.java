package profchoper.course;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        String courseId = resultSet.getString("id");
        String courseName = resultSet.getString("name");
        String courseAlias = resultSet.getString("alias");

        return new Course(courseId, courseName, courseAlias);
    }
}
