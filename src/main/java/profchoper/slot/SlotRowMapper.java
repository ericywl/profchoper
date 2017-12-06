package profchoper.slot;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SlotRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        SlotResultSetExtractor extractor = new SlotResultSetExtractor();
        return extractor.extractData(resultSet);
    }
}
