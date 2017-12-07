package profchoper._misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import profchoper.calendar.CourseCalendarService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class LoginTest {

    @Autowired
    private CourseCalendarService weekCalendar;

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
