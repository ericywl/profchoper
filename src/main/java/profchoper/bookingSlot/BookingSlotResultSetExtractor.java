package profchoper.bookingSlot;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BookingSlotResultSetExtractor implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        String profAlias = resultSet.getString("professor_alias");
        Timestamp startTimestamp = resultSet.getTimestamp("start_time");
        BookingSlot bookingSlot = new BookingSlot(profAlias, startTimestamp);

        int studentId = resultSet.getInt("student_id");
        if (studentId != 0) {
            bookingSlot.setStudentId(studentId);
            bookingSlot.setBookStatus(resultSet.getString("book_status"));
        }

        return bookingSlot;
    }
}