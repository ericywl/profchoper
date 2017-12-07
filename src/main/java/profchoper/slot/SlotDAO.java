package profchoper.slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class SlotDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SlotDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Slot> findAll() {
        String selectSQL = "SELECT * FROM bookings ORDER BY start_time, professor_alias";

        return jdbcTemplate.query(selectSQL, new SlotRowMapper());
    }

    public List<Slot> findByProfAlias(String profAlias) {
        String selectSQL = "SELECT * FROM bookings WHERE professor_alias = ?";

        return jdbcTemplate.query(selectSQL, new Object[]{profAlias}, new SlotRowMapper());
    }

    public List<Slot> findByDateTime(Timestamp startTimestamp) {
        String selectSQL = "SELECT * FROM bookings WHERE start_time = ?";

        return jdbcTemplate.query(selectSQL, new Object[]{startTimestamp}, new SlotRowMapper());
    }

    public List<Slot> findByDateTimeRange(Timestamp startTimestamp, Timestamp endTimestamp) {
        String selectSQL = "SELECT * FROM bookings WHERE start_time BETWEEN ? AND ?";

        return jdbcTemplate.query(selectSQL, new Object[]{startTimestamp, endTimestamp}, new SlotRowMapper());
    }


    // JdbcTemplate returns 1 if success, 0 if failure
    public boolean create(Slot slot) {
        String profAlias = slot.getProfAlias();
        Timestamp startTimestamp = slot.getTimestamp();

        String insertSQL = "INSERT INTO bookings (professor_alias, start_time) VALUES (?, ?) " +
                "ON CONFLICT (professor_alias, start_time) DO NOTHING";

        int i = jdbcTemplate.update(insertSQL, profAlias, startTimestamp);
        return i == 1;
    }

    public boolean update(Slot slot) {
        String profAlias = slot.getProfAlias();
        Timestamp startTimestamp = slot.getTimestamp();
        String bookStatus = slot.getBookStatus();
        int studentId = slot.getStudentId();

        String updateSQL = "UPDATE bookings SET book_status = ?, student_id = ? " +
                "WHERE professor_alias = ? AND start_time = ?";

        int i = jdbcTemplate.update(updateSQL, bookStatus, studentId, startTimestamp);
        return i == 1;
    }

    public boolean delete(Slot slot) {
        String profAlias = slot.getProfAlias();
        Timestamp startTimestamp = slot.getTimestamp();

        String deleteSQL = "DELETE FROM bookings WHERE professor_alias = ? AND start_time = ?";

        int i = jdbcTemplate.update(deleteSQL, profAlias, startTimestamp);
        return i == 1;
    }
}
