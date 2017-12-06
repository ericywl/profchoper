package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import profchoper.professor.Professor;
import profchoper.professor.ProfessorDAO;
import profchoper.professor.ProfessorService;
import profchoper.slot.Slot;
import profchoper.slot.SlotDAO;
import profchoper.student.Student;
import profchoper.student.StudentDAO;

import java.time.LocalDate;
import java.util.*;

@Controller
public class DBTest {
    @Autowired
    private SlotDAO slotDAO;

    @RequestMapping("/")
    String index(Map<String, Object> model) {
        try {
            List<Slot> slotList = slotDAO.findAll();

            model.put("bookings", slotList);
            return "index";
        } catch (Exception ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }

    /*private List<Slot> modelGen(ResultSet bookingRs) throws SQLException, SlotException {
        List<Slot> bookings = new ArrayList<>();

        while (bookingRs.next()) {
            Timestamp timestamp = bookingRs.getTimestamp("start_time");
            String profName = bookingRs.getString("prof_name");
            String profAlias = bookingRs.getString("prof_alias");
            String profEmail = bookingRs.getString("prof_email");
            String profOffice = bookingRs.getString("prof_office");

            Professor prof = new Professor(profName, profAlias, profEmail, profOffice);
            Slot bookingSlot = new Slot(prof, timestamp);

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
