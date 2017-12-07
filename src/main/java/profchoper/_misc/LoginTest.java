package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import profchoper.calendar.CourseWeekCalendarService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class LoginTest {

    @Autowired
    private CourseWeekCalendarService weekCalendar;

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/student")
    public String student(Map<String, Object> model) {
        try {
            LocalDate date = LocalDate.of(2017, 12, 4);
            List<List<String>> cal = weekCalendar.getWeekCalendar(date);

            model.put("calendar", cal);
            return "student";
        } catch (Exception ex) {
            model.put("message", ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/prof")
    public String prof() {
        return "prof";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
