package profchoper.course;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseResultSetExtractor implements ResultSetExtractor{
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Course course = new Course();
        course.setId(resultSet.getString("id"));
        course.setName(resultSet.getString("name"));
        course.setAlias(resultSet.getString("alias"));

        return course;
    }
}
