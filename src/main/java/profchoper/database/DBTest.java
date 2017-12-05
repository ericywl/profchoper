package profchoper.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

            String studentSelect = "SELECT * FROM students ORDER BY id";
            ResultSet studentRs = stmt.executeQuery(studentSelect);
            ArrayList<ArrayList<String>> students = new ArrayList<>();
            while (studentRs.next()) {
                ArrayList<String> student_temp = new ArrayList<>();
                student_temp.add(String.valueOf(studentRs.getInt("id")));
                student_temp.add(studentRs.getString("name"));
                student_temp.add(studentRs.getString("email"));
                student_temp.add(studentRs.getString("course1_id"));
                student_temp.add(studentRs.getString("course2_id"));
                student_temp.add(studentRs.getString("course3_id"));
                student_temp.add(studentRs.getString("course4_id"));

                students.add(student_temp);
            }

            String profSelect = "SELECT * FROM professors ORDER BY cast(course_id AS REAL)";
            ResultSet profRs = stmt.executeQuery(profSelect);
            ArrayList<ArrayList<String>> professors = new ArrayList<>();
            while (profRs.next()) {
                ArrayList<String> prof_temp = new ArrayList<>();
                prof_temp.add(profRs.getString("name"));
                prof_temp.add(profRs.getString("email"));
                prof_temp.add(profRs.getString("office"));
                prof_temp.add(profRs.getString("course_id"));

                professors.add(prof_temp);
            }

            String courseSelect = "SELECT * FROM courses ORDER BY cast(id AS REAL)";
            ResultSet courseRs = stmt.executeQuery(courseSelect);
            ArrayList<ArrayList<String>> courses = new ArrayList<>();
            while (courseRs.next()) {
                ArrayList<String> course_temp = new ArrayList<>();
                course_temp.add(courseRs.getString("id"));
                course_temp.add(courseRs.getString("name"));

                courses.add(course_temp);
            }

            String bookingSelect = "SELECT id, start_time, " +
                    "professors.name as professor_name, book_status, student_id FROM bookings " +
                    "INNER JOIN professors " +
                    "ON bookings.professor_alias = professors.alias " +
                    "WHERE course_id = '50.001'";
            ResultSet bookingRs = stmt.executeQuery(bookingSelect);
            ArrayList<ArrayList<String>> bookings = new ArrayList<>();
            while (bookingRs.next()) {
                ArrayList<String> booking_temp = new ArrayList<>();
                Timestamp timestamp = bookingRs.getTimestamp("start_time");
                SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat time = new SimpleDateFormat("HH:mm");

                booking_temp.add(String.valueOf(bookingRs.getInt("id")));
                booking_temp.add(date.format(timestamp));
                booking_temp.add(time.format(timestamp));
                booking_temp.add(String.valueOf(bookingRs.getString("professor_name")));
                booking_temp.add(String.valueOf(bookingRs.getString("book_status")));
                booking_temp.add(String.valueOf(bookingRs.getInt("student_id")));

                bookings.add(booking_temp);
            }

            model.put("students", students);
            model.put("professors", professors);
            model.put("courses", courses);
            model.put("bookings", bookings);
            return "index";
        } catch (SQLException ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }
}
