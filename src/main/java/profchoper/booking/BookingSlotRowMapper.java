package profchoper.booking;

import org.springframework.jdbc.core.RowMapper;
import profchoper.course.Course;
import profchoper.professor.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

// Extracts results from SQL queries and return booking slot
public class BookingSlotRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        String profName = resultSet.getString("p_name");
        String profAlias = resultSet.getString("p_alias");
        String profEmail = resultSet.getString("p_email");
        String profOffice = resultSet.getString("p_office");

        String courseId = resultSet.getString("c_id");
        String courseName = resultSet.getString("c_name");
        String courseAlias = resultSet.getString("c_alias");

        Course profCourse = new Course(courseId, courseName, courseAlias);
        Professor prof = new Professor(profName, profAlias, profEmail, profOffice, profCourse);

        Timestamp startTimestamp = resultSet.getTimestamp("start_time");
        BookingSlot bookingSlot = new BookingSlot(prof, startTimestamp);

        Integer studentId = resultSet.getInt("student_id");
        if (studentId != 0) {
            bookingSlot.setStudentId(studentId);
            bookingSlot.setStudentName(resultSet.getString("student_name"));
            bookingSlot.setBookStatus(resultSet.getString("book_status"));
        }

        return bookingSlot;
    }
}
