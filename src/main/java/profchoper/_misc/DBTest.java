package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import profchoper.course.Course;
import profchoper.user.Professor;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DBTest {
    @Autowired
    @Qualifier("profChoperDataSource")
    private DataSource dataSource;

    @RequestMapping("/")
    String index(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            Course infoSys = new Course("50.001", "a", "a");
            Professor oka = new Professor("Oka Kurniawan", "a", "a");

            ResultSet courseBookingRs = stmt.executeQuery(selectBookingSlotsByCourse(infoSys));
            model.put("courseBookings", modelGen(courseBookingRs));

            ResultSet profBookingRs = stmt.executeQuery(selectBookingSlotsByProf(oka));
            model.put("profBookings", modelGen(profBookingRs));
            return "index";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }

    private ArrayList<ArrayList<String>> modelGen(ResultSet bookingRs) throws SQLException {
        ArrayList<ArrayList<String>> bookings = new ArrayList<>();

        while (bookingRs.next()) {
            ArrayList<String> booking_temp = new ArrayList<>();
            Timestamp timestamp = bookingRs.getTimestamp("start_time");
            SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");

            booking_temp.add(date.format(timestamp));
            booking_temp.add(time.format(timestamp));
            booking_temp.add(String.valueOf(bookingRs.getString("professor_name")));
            booking_temp.add(String.valueOf(bookingRs.getString("book_status")));
            booking_temp.add(String.valueOf(bookingRs.getInt("student_id")));

            bookings.add(booking_temp);
        }

        return bookings;
    }

    private String selectBookingSlotsByCourse(Course course) {
        return "SELECT start_time, " +
                "professors.name as professor_name, book_status, student_id FROM bookings " +
                "INNER JOIN professors " +
                "ON bookings.professor_alias = professors.alias " +
                "WHERE course_id = '" + course.getId() + "'" +
                "ORDER BY professor_name, start_time";
    }

    private String selectBookingSlotsByProf(Professor professor) {
        return "SELECT start_time, " +
                "professors.name as professor_name, book_status, student_id FROM bookings " +
                "INNER JOIN professors " +
                "ON bookings.professor_alias = professors.alias " +
                "WHERE name = '" + professor.getName() + "'" +
                "ORDER BY professor_name, start_time";
    }
}
