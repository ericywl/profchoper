package profchoper.student;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        StudentResultSetExtractor extractor = new StudentResultSetExtractor();
        return extractor.extractData(resultSet);
    }
}
