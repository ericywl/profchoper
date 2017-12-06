package profchoper.course;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseResultSetExtractor implements ResultSetExtractor{
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        String courseId = resultSet.getString("id");
        String courseName = resultSet.getString("name");
        String courseAlias = resultSet.getString("alias");

        return new Course(courseId, courseName, courseAlias);
    }
}
