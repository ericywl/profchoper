package profchoper.course;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        int courseId = resultSet.getInt("id");
        String courseName = resultSet.getString("name");
        String courseAlias = resultSet.getString("alias");

        return new Course(courseId, courseName, courseAlias);
    }
}
