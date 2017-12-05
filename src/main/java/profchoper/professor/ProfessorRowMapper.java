package profchoper.professor;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        ProfessorResultSetExtractor extractor = new ProfessorResultSetExtractor();
        return extractor.extractData(resultSet);
    }
}
