package profchoper.course;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        CourseResultSetExtractor extractor = new CourseResultSetExtractor();
        return extractor.extractData(resultSet);
    }
}
