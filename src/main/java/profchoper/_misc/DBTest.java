package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import profchoper.course.Course;
import profchoper.course.CourseRepository;

import java.util.*;

@Controller
public class DBTest {
    @Autowired
    @Qualifier("courseRepo")
    private CourseRepository courseRepo;

    @RequestMapping("/")
    String index(Map<String, Object> model) {
        try {
            List<Course> courseList = courseRepo.findAll();

            model.put("courses", courseList);
            return "index";
        } catch (Exception ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }

    /*private List<BookingSlot> modelGen(ResultSet bookingRs) throws SQLException, BookingSlotException {
        List<BookingSlot> bookings = new ArrayList<>();

        while (bookingRs.next()) {
            Timestamp timestamp = bookingRs.getTimestamp("start_time");
            String profName = bookingRs.getString("prof_name");
            String profAlias = bookingRs.getString("prof_alias");
            String profEmail = bookingRs.getString("prof_email");
            String profOffice = bookingRs.getString("prof_office");

            Professor prof = new Professor(profName, profAlias, profEmail, profOffice);
            BookingSlot bookingSlot = new BookingSlot(prof, timestamp);

            bookings.add(bookingSlot);
        }

        return bookings;
    }

    private ResultSet filterBookingSlots(String selection, String arg)
            throws SQLException {

        String selectSQL = "SELECT start_time, " +
                "professors.name as prof_name, " +
                "professors.alias as prof_alias, " +
                "professors.email as prof_email, " +
                "professors.office as prof_office, " +
                "book_status, student_id FROM bookings " +
                "INNER JOIN professors ON bookings.professor_alias = professors.alias " +
                "WHERE " + selection + " = ?" +
                "ORDER BY start_time, prof_name";

        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, arg);

        return preparedStatement.executeQuery();
    }*/
}
