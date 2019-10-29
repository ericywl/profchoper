package profchoper.booking;

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

    public List<BookingSlot> findAll() {
        String selectSQL = "SELECT b.start_time as start_time, b.professor_alias as p_alias, " +
                "b.book_status as book_status, b.student_id as student_id, " +
                "b.student_name as student_name, " +
                "p.name as p_name, p.office as p_office, p.email as p_email, " +
                "c.id as c_id, c.name as c_name, c.alias as c_alias " +
                "FROM bookings b " +
                "JOIN professors p ON b.professor_alias = p.alias " +
                "JOIN courses c ON p.course_id = c.id " +
                "ORDER BY b.start_time, b.professor_alias";

        return jdbcTemplate.query(selectSQL, new BookingSlotRowMapper());
    }

    public List<BookingSlot> findByProfAlias(String profAlias) {
        String selectSQL = "SELECT b.start_time as start_time, b.professor_alias as p_alias, " +
                "b.book_status as book_status, b.student_id as student_id, " +
                "b.student_name as student_name, " +
                "p.name as p_name, p.office as p_office, p.email as p_email, " +
                "c.id as c_id, c.name as c_name, c.alias as c_alias " +
                "FROM bookings b " +
                "JOIN professors p ON b.professor_alias = p.alias " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE b.professor_alias = ? " +
                "ORDER BY b.start_time";

        return jdbcTemplate.query(selectSQL, new Object[]{profAlias}, new BookingSlotRowMapper());
    }

    public List<BookingSlot> findByStudentId(int studentId) {
        String selectSQL = "SELECT b.start_time as start_time, b.professor_alias as p_alias, " +
                "b.book_status as book_status, b.student_id as student_id, " +
                "b.student_name as student_name, " +
                "p.name as p_name, p.office as p_office, p.email as p_email, " +
                "c.id as c_id, c.name as c_name, c.alias as c_alias " +
                "FROM bookings b " +
                "JOIN professors p ON b.professor_alias = p.alias " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE b.student_id = ? " +
                "ORDER BY b.book_status, b.start_time, b.professor_alias";

        return jdbcTemplate.query(selectSQL, new Object[]{studentId}, new BookingSlotRowMapper());
    }

    // Find slots given a specific date and time
    public List<BookingSlot> findByTimestamp(Timestamp startTimestamp) {
        String selectSQL = "SELECT b.start_time as start_time, b.professor_alias as p_alias, " +
                "b.book_status as book_status, b.student_id as student_id, " +
                "b.student_name as student_name, " +
                "p.name as p_name, p.office as p_office, p.email as p_email, " +
                "c.id as c_id, c.name as c_name, c.alias as c_alias " +
                "FROM bookings b " +
                "JOIN professors p ON b.professor_alias = p.alias " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE b.start_time = ? " +
                "ORDER BY b.start_time, b.professor_alias";

        return jdbcTemplate.query(selectSQL, new Object[]{startTimestamp}, new BookingSlotRowMapper());
    }

    // Find slots given a range of datetime
    public List<BookingSlot> findByTimestampRange(Timestamp startTimestamp, Timestamp endTimestamp) {
        String selectSQL = "SELECT b.start_time as start_time, b.professor_alias as p_alias, " +
                "b.book_status as book_status, b.student_id as student_id, " +
                "b.student_name as student_name, " +
                "p.name as p_name, p.office as p_office, p.email as p_email, " +
                "c.id as c_id, c.name as c_name, c.alias as c_alias " +
                "FROM bookings b " +
                "JOIN professors p ON b.professor_alias = p.alias " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE b.start_time BETWEEN ? AND ? " +
                "ORDER BY b.start_time, b.professor_alias";

        return jdbcTemplate.query(selectSQL, new Object[]{startTimestamp, endTimestamp},
                new BookingSlotRowMapper());
    }

    public BookingSlot findByProfAndTimestamp(String profAlias, Timestamp startTimestamp) {
        String selectSQL = "SELECT b.start_time as start_time, b.professor_alias as p_alias, " +
                "b.book_status as book_status, b.student_id as student_id, " +
                "b.student_name as student_name, " +
                "p.name as p_name, p.office as p_office, p.email as p_email, " +
                "c.id as c_id, c.name as c_name, c.alias as c_alias " +
                "FROM bookings b " +
                "JOIN professors p ON b.professor_alias = p.alias " +
                "JOIN courses c ON p.course_id = c.id " +
                "WHERE b.professor_alias = ? AND b.start_time = ?";

        return (BookingSlot) jdbcTemplate.queryForObject(selectSQL,
                new Object[]{profAlias, startTimestamp}, new BookingSlotRowMapper());
    }


    // JdbcTemplate returns 1 if success, 0 if failure
    public boolean create(BookingSlot slot) {
        String profAlias = slot.getProfessor().getAlias();
        Timestamp startTimestamp = slot.getTimestamp();

        String insertSQL = "INSERT INTO bookings (professor_alias, start_time) VALUES (?, ?) " +
                "ON CONFLICT (professor_alias, start_time) DO " +
                "UPDATE SET book_status = ?, student_id = ?, student_name = ?";

        int i = jdbcTemplate.update(insertSQL, profAlias, startTimestamp, "AVAILABLE", null, null);
        return i == 1;
    }

    public boolean update(BookingSlot slot) {
        String profAlias = slot.getProfessor().getAlias();
        Timestamp startTimestamp = slot.getTimestamp();
        String bookStatus = slot.getBookStatus();
        Integer studentId = slot.getStudentId();
        String studentName = slot.getStudentName();

        String updateSQL = "UPDATE bookings SET book_status = ?, student_id = ?, student_name = ? " +
                "WHERE professor_alias = ? AND start_time = ?";

        int i = jdbcTemplate.update(updateSQL, bookStatus, studentId, studentName,
                profAlias, startTimestamp);
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
