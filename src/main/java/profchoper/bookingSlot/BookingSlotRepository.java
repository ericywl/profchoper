package profchoper.bookingSlot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class BookingSlotRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingSlotRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookingSlotModel> findAll() {
        String selectSQL = "SELECT * FROM bookings ORDER BY start_time, professor_alias";

        return jdbcTemplate.query(selectSQL, new BookingSlotModelRowMapper());
    }

    public List<BookingSlotModel> findByProfAlias(String profAlias) {
        String selectSQL = "SELECT * FROM bookings WHERE professor_alias = ? ORDER BY start_time";

        return jdbcTemplate.query(selectSQL, new Object[]{profAlias}, new BookingSlotModelRowMapper());
    }

    public List<BookingSlotModel> findByDateTime(Timestamp startTimestamp) {
        String selectSQL = "SELECT * FROM bookings WHERE start_time = ? ORDER BY professor_alias";

        return jdbcTemplate.query(selectSQL, new Object[]{startTimestamp}, new BookingSlotModelRowMapper());
    }

    public List<BookingSlotModel> findByDateTimeRange(Timestamp startTimestamp, Timestamp endTimestamp) {
        String selectSQL = "SELECT * FROM bookings WHERE start_time BETWEEN ? AND ? " +
                "ORDER BY start_time, professor_alias";

        return jdbcTemplate.query(selectSQL, new Object[]{startTimestamp, endTimestamp},
                new BookingSlotModelRowMapper());
    }


    // JdbcTemplate returns 1 if success, 0 if failure
    public boolean create(BookingSlot slot) {
        String profAlias = slot.getProfessor().getAlias();
        Timestamp startTimestamp = slot.getTimestamp();

        String insertSQL = "INSERT INTO bookings (professor_alias, start_time) VALUES (?, ?) " +
                "ON CONFLICT (professor_alias, start_time) DO NOTHING";

        int i = jdbcTemplate.update(insertSQL, profAlias, startTimestamp);
        return i == 1;
    }

    public boolean update(BookingSlot slot) {
        String profAlias = slot.getProfessor().getAlias();
        Timestamp startTimestamp = slot.getTimestamp();
        String bookStatus = slot.getBookStatus();
        int studentId = slot.getStudent().getId();

        String updateSQL = "UPDATE bookings SET book_status = ?, student_id = ? " +
                "WHERE professor_alias = ? AND start_time = ?";

        int i = jdbcTemplate.update(updateSQL, bookStatus, studentId, startTimestamp);
        return i == 1;
    }

    public boolean delete(BookingSlot slot) {
        String profAlias = slot.getProfessor().getAlias();
        Timestamp startTimestamp = slot.getTimestamp();

        String deleteSQL = "DELETE FROM bookings WHERE professor_alias = ? AND start_time = ?";

        int i = jdbcTemplate.update(deleteSQL, profAlias, startTimestamp);
        return i == 1;
    }
}
