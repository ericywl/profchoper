package profchoper.bookingslot;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingSlotRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        BookingSlotResultSetExtractor extractor = new BookingSlotResultSetExtractor();
        return extractor.extractData(resultSet);
    }
}
