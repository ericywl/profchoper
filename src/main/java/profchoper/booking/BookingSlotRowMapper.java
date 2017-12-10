package profchoper.booking;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BookingSlotRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
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
